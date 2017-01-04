package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static server.SimpleHttpRequest.Method.GET;

/**
 * Created by mustafa.hussain on 02/01/17.
 */
public class SimpleHttpRequestBuilder {

    public static SimpleHttpRequest buildSimpleHttpRequest(BufferedReader in) {
        SimpleHttpRequest request = new SimpleHttpRequest();

        String input = null;
        try {
            String str = in.readLine();
            if (str == null) {
                throw new RuntimeException();
            }

            String parts[] = str.split(" ");
            String method = parts[0];
            String path = parts[1];

            if (!method.equals("GET")) {
                throw new RuntimeException();
            }

            Map<String, String> headers = new HashMap<>();
            for (input = in.readLine(); input != null && !input.equals(""); input = in.readLine()) {
                System.out.println(input);
                String[] splits = input.split(" ");
                headers.put(splits[0], splits[1]);
                System.out.println(input);
            }

            request.setHeaders(headers);
            request.setMethod(GET);
            request.setPath(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }
}
