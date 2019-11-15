package Handlers;

import Response.ClearResponse;
import Services.Clear;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class ClearRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        Clear clearService = new Clear();
        ClearResponse clearResp = (ClearResponse) clearService.clear();
        String jsonString = gson.toJson(clearResp);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        writeString(jsonString, exchange.getResponseBody());
        exchange.getResponseBody().close();
    }
    public void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
