package server;

import java.io.PrintWriter;

/**
 * Created by mustafa.hussain on 02/01/17.
 */
public class ClientHandlerThread implements Runnable {

    private IHttpRequestHandler requestHandler;
    private SimpleHttpRequest request;
    private SimpleHttpResponse response;
    private PrintWriter printWriter;

    public ClientHandlerThread (IHttpRequestHandler requestHandler, SimpleHttpRequest request, PrintWriter printWriter) {
        this.requestHandler = requestHandler;
        this.request = request;
        this.printWriter = printWriter;
    }

    @Override
    public void run() {

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        response = requestHandler.handle(request);
        printWriter.write(buildHttpResponse(response));
        printWriter.flush();
        printWriter.close();
    }

    private String buildHttpResponse(SimpleHttpResponse response) {
        StringBuilder str = new StringBuilder();
        str.append("HTTP/1.1 " + response.getHttpResponseCode() + " OK\r\n");
        str.append("Content-Length: " + response.getResponseBody().length() + "\r\n");
        str.append("Content-Type: text/html\r\n\r\n\r\n");
        str.append(response.getResponseBody());

        return str.toString();
    }

    public SimpleHttpResponse getResponse() {
        return this.response;
    }
}
