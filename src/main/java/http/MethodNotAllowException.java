package http;

import javax.xml.ws.http.HTTPException;

/**
 * Created by Niki on 2018/4/11 8:51
 */
public class MethodNotAllowException extends RuntimeException {
    public MethodNotAllowException() {
        super("Request Method Not Allow! Only Allow Method :" + HttpMethod.allMethods());
    }
}
