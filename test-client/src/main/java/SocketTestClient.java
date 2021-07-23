import Serializer.KryoSerializer;
import Socket.client.SocketClient;
import api.HelloObject;
import api.HelloService;
import client.RpcClientProxy;

/**
 * 测试用消费者（客户端）
 * Created by tangssst@qq.com on 2021/07/21
 */
public class SocketTestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9999);
        client.setSerializer(new KryoSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
