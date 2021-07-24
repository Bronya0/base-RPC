package factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例工厂
 * @author tangssst@qq.com
 */
public class SingletonFactory {

    //构造方法私有化
    private SingletonFactory() {}


    private static Map<Class, Object> objectMap = new HashMap<>();

    //提供一个全局访问点
    public static <T> T getInstance(Class<T> clazz) {
        Object instance = objectMap.get(clazz);
        synchronized (clazz) {
            if(instance == null) {
                try {
                    instance = clazz.newInstance();
                    objectMap.put(clazz, instance);
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        return clazz.cast(instance);
    }
}
