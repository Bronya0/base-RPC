package test;

import api.HelloService;
import serializer.CommonSerializer;
import transport.netty.server.NettyServer;

/**
 * Created by tangssst@qq.com on 2021/07/21
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.publishService(helloService, HelloService.class);
    }
}
