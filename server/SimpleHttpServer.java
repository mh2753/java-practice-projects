package server;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mustafa.hussain on 31/12/16.
 */
public class SimpleHttpServer {

    private Map<String, IHttpRequestHandler> getRequestHandlerMap = new HashMap<>();
    private Map<String, IHttpRequestHandler> putRequestHandlerMap = new HashMap<>();
    private Map<String, IHttpRequestHandler> postRequestHandlerMap = new HashMap<>();
    private Map<String, IHttpRequestHandler> deletetRequestHandlerMap = new HashMap<>();

    private ServerSocket serverSocket;

    private boolean isRunning = true;

    private static final int DEFAULT_PORT = 80;
    /*
    Start a simple server listening to port 80
     */
    public SimpleHttpServer() {
        this(DEFAULT_PORT);
    }

    /*
    Start a simple server listening to connections on specified port
     */
    public SimpleHttpServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {

        try {
            while (isRunning) {
                Socket socket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                SimpleHttpRequest request = SimpleHttpRequestBuilder.buildSimpleHttpRequest(in);
                IHttpRequestHandler handler = getRequestHandler(request);

                if (handler == null) {
                    SimpleHttpResponse response = new SimpleHttpResponse();
//                    response.setResponseBody("No Handler found for request \r\n");

                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.write(buildNotFoundResponse(response));
                    printWriter.flush();
                    printWriter.close();

                    continue;
                }

                ClientHandlerThread clientHandlerThread = new ClientHandlerThread(
                        handler,
                        request,
                        new PrintWriter(socket.getOutputStream(), true));

                Thread thread = new Thread(clientHandlerThread);
                thread.start();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildNotFoundResponse(SimpleHttpResponse response) {
        StringBuilder str = new StringBuilder();
        str.append("HTTP/1.1 404 Not Found\r\n");
        if (response.getResponseBody() != null) {
            str.append("Content-Length: " + response.getResponseBody().length() + "\r\n");
            str.append(response.getResponseBody());
        }
        str.append("Content-Type: text/html\r\n\r\n\r\n");

        return str.toString();
    }

    private IHttpRequestHandler getRequestHandler(SimpleHttpRequest request) {
        if (getRequestHandlerMap.containsKey(request.getPath())) {
            return getRequestHandlerMap.get(request.getPath());
        }

        return null;
    }

    public void stop() {
        this.isRunning = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHandler(SimpleHttpRequest.Method httpMethod, String path, IHttpRequestHandler requestHandler) {
        switch (httpMethod) {
            case GET:
                getRequestHandlerMap.put(path, requestHandler);
                break;
            case POST:
                postRequestHandlerMap.put(path, requestHandler);
                break;
            case PUT:
                putRequestHandlerMap.put(path, requestHandler);
                break;
            case DELETE:
                deletetRequestHandlerMap.put(path, requestHandler);
                default:
                    throw new NotImplementedException();
        }
    }
}