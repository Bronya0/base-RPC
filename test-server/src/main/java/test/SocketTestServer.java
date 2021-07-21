package test;

import Socket.server.SocketServer;
import api.HelloService;
import registry.DefaultServiceRegistry;
import registry.ServiceRegistry;

/**
 * 测试用服务提供方（服务端）
 * Created by tangssst@qq.com on 2021/07/21
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }
}
