import api.HelloObject;
import api.HelloService;
import serializer.CommonSerializer;
import transport.RpcClientProxy;
import transport.Socket.client.SocketClient;

/**
 * 测试用消费者（客户端）
 * Created by tangssst@qq.com on 2021/07/21
 */
public class SocketTestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
