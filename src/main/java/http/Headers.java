package http;

import java.util.HashMap;
import java.util.List;

/**
 * 请求头所有信息
 * Created by Niki on 2018/4/11 9:03
 */
public class Headers extends HashMap<String, Header> {
    public Headers(List<String> headerLines) {
        headerLines.forEach(n -> {
            Header h = new Header(n);
            put(h.getName(), h);
        });
    }

    public Headers() {
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String separator = System.getProperty("Line.separator");
        this.forEach((key, header) ->{
            builder.append(header.toString()).append(separator);
        });
        return builder.toString();
    }
}
