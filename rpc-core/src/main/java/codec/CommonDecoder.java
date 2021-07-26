package codec;

import Exception.RpcException;
import entity.RpcRequest;
import entity.RpcResponse;
import enumeration.PackageType;
import enumeration.RpcError;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializer.CommonSerializer;

import java.util.List;

/**
 * 通用的解码器，将字符数组转为对象
 * 在Netty里面，有四个核心概念，分别是：
 * 1、Channel，一个客户端与服务器通信的通道
 * 2、ChannelHandler，业务逻辑处理器，分为ChannelInboundHandler和ChannelOutboundHandler：
 *  1)ChannelInboundHandler，输入数据处理器
 *  2)ChannelOutboundHandler，输出业务处理器
 * 通常情况下，业务逻辑都是存在于ChannelHandler之中
 * 3、ChannelPipeline，用于存放ChannelHandler的容器
 * 4、ChannelContext，通信管道的上下文
 * ——————————————————————————————————————
 * 主要就是一些字段的校验，比较重要的就是取出序列化器的编号，以获得正确的反序列化方式，
 * 并且读入 length 字段来确定数据包的长度（防止粘包），最后读入正确大小的字节数组，反序列化成对应的对象。
 *
 * Created by tangssst@qq.com on 2021/07/21
 */
public class CommonDecoder extends ReplayingDecoder {

    private static final Logger logger = LoggerFactory.getLogger(CommonDecoder.class);
    //4字节魔数，标识一个协议包
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //从输入流中读取四个字节.用最开始的4bit存储后续data部分的长度
        int magic = in.readInt();
        if(magic != MAGIC_NUMBER) {
            logger.error("不识别的协议包: {}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        int packageCode = in.readInt();
        Class<?> packageClass;

        if(packageCode == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        } else if(packageCode == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        } else {
            logger.error("不识别的数据包: {}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        int serializerCode = in.readInt();
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
        if(serializer == null) {
            logger.error("不识别的反序列化器: {}", serializerCode);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        //将字节流反序列化为对象
        Object obj = serializer.deserialize(bytes, packageClass);
        out.add(obj);
    }
}
