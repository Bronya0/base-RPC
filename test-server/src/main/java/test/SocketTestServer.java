package test;

import annotation.ServiceScan;
import serializer.CommonSerializer;
import transport.RpcServer;
import transport.Socket.server.SocketServer;

/**
 * 测试用服务提供方（服务端）
 * Created by tangssst@qq.com on 2021/07/21
 */
@ServiceScan
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        server.start();
    }
}
