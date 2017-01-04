package server;

/**
 * Created by mustafa.hussain on 01/01/17.
 */
public class Driver {

    public static void main(String args[]) {
        SimpleHttpServer server = new SimpleHttpServer(9999);
        server.addHandler(SimpleHttpRequest.Method.GET, "/", new HelloWorldRequestHandler());
        server.addHandler(SimpleHttpRequest.Method.GET, "/mustafa", new HelloWorldRequestHandler());
        server.start();
    }
}
