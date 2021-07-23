package provider;

/**
 * @author tangssst@qq.com
 */
public  interface ServiceProvider {


    <T> void addServiceProvider(T service);

    Object getServiceProvider(String serviceName);

}
