package server;

/**
 * Created by mustafa.hussain on 01/01/17.
 */
public class HelloWorldRequestHandler implements IHttpRequestHandler {
    @Override
    public SimpleHttpResponse handle(SimpleHttpRequest request) {
        SimpleHttpResponse response = new SimpleHttpResponse();

        response.setHttpResponseCode(200);
        response.setResponseBody("<h1>It Works!</h1>");

        return response;
    }
}
