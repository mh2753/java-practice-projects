package server;

/**
 * Created by mustafa.hussain on 01/01/17.
 */
public interface IHttpRequestHandler {

    SimpleHttpResponse handle(SimpleHttpRequest request);

}
