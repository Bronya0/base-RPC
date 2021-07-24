package registry;

import Exception.RpcException;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import enumeration.RpcError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.NacosUtil;

import java.net.InetSocketAddress;

/**
 * @author tangssst@qq.com
 */
public class NacosServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    private final NamingService namingService;

    public NacosServiceRegistry() {
        this.namingService = NacosUtil.getNacosNamingService();
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtil.registerService(namingService, serviceName, inetSocketAddress);
        } catch (NacosException e) {
            logger.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
