package codec;

import entity.RpcRequest;
import enumeration.PackageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import serializer.CommonSerializer;

/**
 * 通用的编码器，把对象编码成字节数组
 * CommonEncoder 继承了MessageToByteEncoder 类，就是把 Message（实际要发送的对象）转化成Byte数组。
 * CommonEncoder 的工作很简单，就是把 RpcRequest 或者 RpcResponse 包装成协议包。
 * 将各个字段写到管道里，这里serializer.getCode() 获取序列化器的编号，
 * 之后使用传入的序列化器将请求或响应包序列化为字节数组写入管道即可。
 *
 * Created by tangssst@qq.com on 2021/07/21
 */
public class CommonEncoder extends MessageToByteEncoder {

    // 4字节魔数，表示一个协议包
    private static final int MAGIC_NUMBER = 0xCAFEBABE;
    //传入序列化器
    private final CommonSerializer serializer;
    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    //Package Type，标明这是一个请求还是响应
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER);
        if(msg instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        //serializer.getCode()表示获取序列化器的编号
        out.writeInt(serializer.getCode());
        //序列化
        byte[] bytes = serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
