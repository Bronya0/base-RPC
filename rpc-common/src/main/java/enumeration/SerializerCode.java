package enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by tangssst@qq.com on 2021/07/21
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {

    KRYO(0),
    JSON(1);

    private final int code;

}
