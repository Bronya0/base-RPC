package test;

import api.HelloService;
import netty.server.NettyServer;
import registry.DefaultServiceRegistry;
import registry.ServiceRegistry;

/**
 * Created by tangssst@qq.com on 2021/07/21
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }
}
