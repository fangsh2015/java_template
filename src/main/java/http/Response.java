package http;

/**
 * Created by Niki on 2018/4/11 9:23
 */
public class Response {
    private HttpMethod httpMethod;
    private Headers headers;
    private HttpBody body;
    private String version;
    private int code;
    private String message;
}
