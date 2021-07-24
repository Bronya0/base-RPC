package transport;

import entity.RpcRequest;
import entity.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transport.Socket.client.SocketClient;
import transport.netty.client.NettyClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * rpc客户端动态代理
 * Created by tangssst@qq.com on 2021/07/21
 */
public class RpcClientProxy implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);

    private final RpcClient client;

    public RpcClientProxy(RpcClient client) {
        this.client = client;
    }

    /**
     * 用于生成代理对象
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    /**
     * 实现invoke()方法，来指明代理对象的方法被调用时的动作.
     * 生成一个RpcRequest对象，发送出去，然后返回从服务端接收到的结果
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        logger.info("调用方法: {}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = new RpcRequest(UUID.randomUUID().toString(), method.getDeclaringClass().getName(),
                method.getName(), args, method.getParameterTypes(),false);
        Object result = null;
        if (client instanceof NettyClient) {
            CompletableFuture<RpcResponse> completableFuture = (CompletableFuture<RpcResponse>) client.sendRequest(rpcRequest);
            try {
                result = completableFuture.get().getData();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("方法调用请求发送失败", e);
                return null;
            }
        }
        if (client instanceof SocketClient) {
            RpcResponse rpcResponse = (RpcResponse) client.sendRequest(rpcRequest);
            result = rpcResponse.getData();
        }
        return result;
    }
}
