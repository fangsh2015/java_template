package http;

/**
 * HTTP请求体
 * Created by Niki on 2018/4/11 9:26
 */
public abstract class HttpBody {
    /* 获取Content-Length */
    public abstract int getContentLength();
    /* 获取Content-Type HTTP传输中的响应体*/
    public abstract String getContentType();

    public abstract String toString();
}
