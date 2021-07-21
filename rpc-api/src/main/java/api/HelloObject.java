package api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * HelloService的vo对象
 * Created by tangssst@qq.com on 2021/07/21
 */
@Data
@AllArgsConstructor
public class HelloObject implements Serializable {
    private Integer id;
    private String message;
}
