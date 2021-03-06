package Exception;

import enumeration.RpcError;

/**
 * RPC调用异常
 * Created by tangssst@qq.com on 2021/07/21
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }

}
