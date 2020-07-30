package jvm.dynamic_load;

/**
 * Created by Niki on 2018/4/17 19:59
 */
public class User {
    private String name;
    public static int code = 10 ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getCode() {
        return code;
    }

    public static void setCode(int code) {
        User.code = code;
    }
}
