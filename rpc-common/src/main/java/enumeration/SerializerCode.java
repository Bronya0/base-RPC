package enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by tangssst@qq.com on 2021/07/21
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {

    JSON(1);

    private final int code;

}
