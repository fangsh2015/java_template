package tools.jsonrpc4j;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Niki on 2020/11/6 20:02
 */
@Data
public class User implements Serializable {
    private int userId;
    private String name;
    private int age;

}
