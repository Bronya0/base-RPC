package Loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 负载均衡接口
 * @author tangssst@qq.com
 */
public interface LoadBalancer {
    Instance select(List<Instance> instances);
}
