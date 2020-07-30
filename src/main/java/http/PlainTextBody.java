package http;

/**
 * Plain类型的请求体
 * Created by Niki on 2018/4/11 9:32
 */
public class PlainTextBody extends HttpBody {
    private String bodyContent;

    public PlainTextBody(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    @Override
    public int getContentLength() {
        return this.bodyContent.getBytes().length;
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }

    @Override
    public String toString() {
        return this.bodyContent;
    }
}
