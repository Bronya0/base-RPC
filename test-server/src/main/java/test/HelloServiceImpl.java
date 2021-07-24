package test;

import api.HelloObject;
import api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端对通用接口的实现类
 * Created by tangssst@qq.com on 2021/07/21
 */
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到消息：{}", object.getMessage());
        return "本次处理来自Netty服务";
    }

}