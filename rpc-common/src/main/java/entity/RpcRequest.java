package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 服务端需要哪些信息，才能唯一确定服务端需要调用的接口的方法呢？
 * 接口的名字，和方法的名字，但是由于方法重载的缘故，还需要这个方法的所有参数的类型，
 * 客户端调用时，还需要传递参数的实际值，那么服务端知道以上四个条件，就可以找到这个方法并且调用了。
 * 把这四个条件写到一个对象里，到时候传输时传输这个对象就行了。即RpcRequest对象
 * Created by tangssst@qq.com on 2021/07/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {
    /**
     * 请求号
     */
    private String requestId;
    /**
     * 待调用接口名称
     */
    private String interfaceName;
    /**
     * 待调用方法名称
     */
    private String methodName;
    /**
     * 调用方法的参数值
     */
    private Object[] parameters;
    /**
     * 调用方法的参数类型
     */
    private Class<?>[] paramTypes;
    /**
     * 是否是心跳包
     */
    private Boolean heartBeat;

}
