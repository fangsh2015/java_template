package http;

/**
 * 请求头对象
 * Created by Niki on 2018/4/11 9:01
 */
public class Header {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Header(String line) {
        String[] kv = line.split(" ");
        this.name = kv[0];
        this.value = kv[1];
    }

    @Override
    public String toString() {
        return this.name + ":" + this.value;
    }
}
