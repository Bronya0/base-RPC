package enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by tangssst@qq.com on 2021/07/21
 */
@AllArgsConstructor
@Getter
public enum RpcError {

    SERVICE_INVOCATION_FAILURE("服务调用出现失败"),
    SERVICE_CAN_NOT_BE_NULL("注册的服务不得为空");

    private final String message;
}
