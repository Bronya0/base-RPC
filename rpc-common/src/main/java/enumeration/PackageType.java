package enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 包类型，是请求还是响应
 * Created by tangssst@qq.com on 2021/07/21
 */
@AllArgsConstructor
@Getter
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;

}
