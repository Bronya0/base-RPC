package test;

import api.HelloService;
import serializer.CommonSerializer;
import transport.Socket.server.SocketServer;

/**
 * 测试用服务提供方（服务端）
 * Created by tangssst@qq.com on 2021/07/21
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl2();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        socketServer.publishService(helloService, HelloService.class);
    }
}
