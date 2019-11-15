package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String urlPath = exchange.getRequestURI().toString();
        if (urlPath.equals("/")){
            urlPath = "/index.html";
        }
        String filePath = "web" + urlPath;
        File myFile = new File(filePath);
        if (!myFile.exists()){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            File error = new File("web/HTML/404.html");
            OutputStream respBody = exchange.getResponseBody();
            Files.copy(error.toPath(), respBody);
            exchange.getResponseBody().close();
        }
        else{
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            Files.copy(myFile.toPath(), respBody);
            exchange.getResponseBody().close();
        }
    }
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
