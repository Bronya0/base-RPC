package factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例工厂模式
 * @author tangssst@qq.com
 */
public class SingletonFactory {

    //构造方法私有化
    private SingletonFactory() {}

    //存放类——实例的map
    private static Map<Class, Object> objectMap = new HashMap<>();

    //提供一个全局访问点，传入类则将其实例放入map
    public static <T> T getInstance(Class<T> clazz) {
        Object instance = objectMap.get(clazz);
        synchronized (clazz) {
            //map里没有则去创建实例
            if(instance == null) {
                try {
                    //如果在编译时不知道什么类型的对象，使用newInstance（）
                    instance = clazz.newInstance();
                    objectMap.put(clazz, instance);
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        //clazz.cast用于强转obj为T类型
        return clazz.cast(instance);
    }
}
