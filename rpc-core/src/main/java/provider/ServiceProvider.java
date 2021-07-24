package provider;

/**
 * @author tangssst@qq.com
 */
public  interface ServiceProvider {

    <T> void addServiceProvider(T service, String serviceClass);

    Object getServiceProvider(String serviceName);

}
