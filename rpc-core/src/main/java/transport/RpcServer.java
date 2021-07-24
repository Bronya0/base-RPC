package transport;

import serializer.CommonSerializer;

/**
 * 服务端通用接口
 * Created by tangssst@qq.com on 2021/07/21
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    <T> void publishService(T service, Class<T> serviceClass);
}
