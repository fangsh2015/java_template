package http;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 模拟HTTP协议的Request请求
 * Created by Niki on 2018/4/11 8:46
 */
public class Request {
    private HttpMethod method;
    private String path;
    private String version;

    public Request validate(BufferedReader input) {
        String line = null;
        boolean isFirstLine = true;
        Request request = new Request();
        List<String> headerLines = new ArrayList<>();
        try {

            while ((line = input.readLine()) != null) {
                if (isFirstLine) {
                    initRequestProps(line);
                    isFirstLine = false;
                    continue;
                }
                if (line.length() == 0) {
                    request.initRequestHeader(headerLines);
                    break;
                } else {
                    headerLines.add(line);
                }
            }
        } catch (IOException e) {

        }
        return request;
    }

    /**
     * 请求的第一行，解析出请求的方法，路径以及版本
     *
     * @param firstLine
     */
    public void initRequestProps(String firstLine) {
        String[] pros = firstLine.split(" ");
        String method = pros[0];
        String path = pros[1];
        String version = pros[2];
    }

    /**
     * 解析头信息
     *
     * @param headerLines
     */
    public void initRequestHeader(List<String> headerLines) {
        Headers headers = new Headers(headerLines);
    }
}
