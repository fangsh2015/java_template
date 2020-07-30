package http;

import test.Collections;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Http的请求方法对象
 * Created by Niki on 2018/4/11 8:47
 */
public enum HttpMethod {
    GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, CONNECT;
    public static HttpMethod parse(String method) {
        switch (method.trim().toUpperCase()){
            case "GET":
                return GET;
            case "POST":
                return POST;
            case "PUT":
                return PUT;
            case "DELETE":
                return DELETE;
            case "HEAD":
                return HEAD;
            case "OPTIONS":
                return OPTIONS;
            case "TRACE":
                return TRACE;
            case "CONNECT":
                return CONNECT;
        }
        throw new MethodNotAllowException();
    }

    public static String allMethods(){
        List<String> result = Arrays.stream(HttpMethod.values()).map(e -> e.name()).collect(Collectors.toList());
        return result.toString();
    }
}
