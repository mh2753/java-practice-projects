package server;

import java.util.Map;

/**
 * Created by mustafa.hussain on 31/12/16.
 */
public class SimpleHttpRequest {

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static enum Method{
        POST, GET, PUT, DELETE
    }

    private Method method;

    private Map<String, String> headers;

    private String requestBody;

    private String path;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}
