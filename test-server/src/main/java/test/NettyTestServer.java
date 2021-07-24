package test;

import annotation.ServiceScan;
import serializer.CommonSerializer;
import transport.RpcServer;
import transport.netty.server.NettyServer;

/**
 * Created by tangssst@qq.com on 2021/07/21
 */
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }
}
