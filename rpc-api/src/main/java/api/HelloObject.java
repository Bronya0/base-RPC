package api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * HelloService的vo对象
 * Created by tangssst@qq.com on 2021/07/21
 */
@Data
@AllArgsConstructor
public class HelloObject {
    private Integer id;
    private String message;
}
