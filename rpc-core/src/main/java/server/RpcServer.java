package server;

import serializer.CommonSerializer;

/**
 * 服务端通用接口
 * Created by tangssst@qq.com on 2021/07/21
 */
public interface RpcServer {

    void start();

    void setSerializer(CommonSerializer serializer);

    <T> void publishService(Object service, Class<T> serviceClass);
}
