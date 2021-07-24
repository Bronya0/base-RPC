package Loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author tangssst@qq.com
 */
public interface LoadBalancer {
    Instance select(List<Instance> instances);
}
