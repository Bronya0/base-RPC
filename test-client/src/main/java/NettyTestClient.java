import Serializer.ProtobufSerializer;
import api.HelloObject;
import api.HelloService;
import client.RpcClient;
import client.RpcClientProxy;
import netty.client.NettyClient;

/**
 * 测试用Netty消费者
 * Created by tangssst@qq.com on 2021/07/21
 */
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        client.setSerializer(new ProtobufSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);

    }

}
