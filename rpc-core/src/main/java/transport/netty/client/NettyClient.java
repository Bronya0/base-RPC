package transport.netty.client;

import Exception.RpcException;
import Loadbalancer.LoadBalancer;
import Loadbalancer.RandomLoadBalancer;
import entity.RpcRequest;
import entity.RpcResponse;
import enumeration.RpcError;
import factory.SingletonFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import registry.NacosServiceDiscovery;
import registry.ServiceDiscovery;
import serializer.CommonSerializer;
import transport.RpcClient;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * NIO方式消费侧客户端类
 * Created by tangssst@qq.com
 */

public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    //EventLoopGroup 是 Netty 内部定一个一个线程池结构，用于执行来自 netty 内部的事件监听与逻辑处理。
    private static final EventLoopGroup group;
    //bootstrap 是我们的 netty 服务建造者, 用于定义启动我们的 netty 服务
    private static final Bootstrap bootstrap;

    static {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class);
    }

    private final ServiceDiscovery serviceDiscovery;
    private final CommonSerializer serializer;
    //未处理请求列表，ConcurrentHashMap
    private final UnprocessedRequests unprocessedRequests;

    //只制定了序列化器时，默认为随机均衡
    public NettyClient(Integer serializer) {
        this(serializer, new RandomLoadBalancer());
    }
    //都指定
    public NettyClient(Integer serializer, LoadBalancer loadBalancer) {
        this.serviceDiscovery = new NacosServiceDiscovery(loadBalancer);
        this.serializer = CommonSerializer.getByCode(serializer);
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
    }

    @Override
    public CompletableFuture<RpcResponse> sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        //Netty 所有的 I/O 操作都是异步。因为一个操作可能无法立即返回，我们需要有一种方法在以后确定它的结果。
        CompletableFuture<RpcResponse> resultFuture = new CompletableFuture<>();
        try {
            //根据接口名获得服务地址
            InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
            if (!channel.isActive()) {
                group.shutdownGracefully();
                return null;
            }
            unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);
            //channel将rpcRequest对象写出-数据冲刷，添加ChannelFutureListener以便在写操作完成后接收通知
            channel.writeAndFlush(rpcRequest).addListener((ChannelFutureListener) future1 -> {
                if (future1.isSuccess()) {
                    logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                } else {
                    future1.channel().close();
                    resultFuture.completeExceptionally(future1.cause());
                    logger.error("发送消息时有错误发生: ", future1.cause());
                }
            });
        } catch (InterruptedException e) {
            unprocessedRequests.remove(rpcRequest.getRequestId());
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return resultFuture;
    }


}
