package Handlers;

import Request.LoadRequest;
import Response.ErrorMessage;
import Response.LoadResponse;
import Services.Load;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Gson gson = new Gson();
            String temp = readString(exchange.getRequestBody());
            LoadRequest loadRequest = gson.fromJson(temp, LoadRequest.class);
            Load load = new Load();
            LoadResponse loadResponse = (LoadResponse) load.load(loadRequest);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            String jsonString = gson.toJson(loadResponse);
            writeString(jsonString, exchange.getResponseBody());
            exchange.getResponseBody().close();

            exchange.close();
        }catch(Exception e){
            Gson gson = new Gson();
            ErrorMessage temp = new ErrorMessage(e.getMessage());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            String jsonString = gson.toJson(temp);
            writeString(jsonString, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }
    }
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

}
