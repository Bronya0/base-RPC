package test;

import api.HelloService;
import server.RpcServer;

/**
 * 已经在上面实现了一个HelloService的实现类HelloServiceImpl的实现类了，
 * 只需要创建一个RpcServer并且把这个实现类注册进去就行了
 * Created by tangssst@qq.com on 2021/07/21
 */
public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}