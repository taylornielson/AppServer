package Handlers;

import Response.ErrorMessage;
import Response.FillResponse;
import Services.Fill;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Arrays;

public class FillRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String urlPath = exchange.getRequestURI().toString();
        String params[] = urlPath.split("/");
        params = Arrays.copyOfRange(params, 1,params.length);
        Gson gson = new Gson();
        Fill fillService = new Fill();
        try {
            if (params.length == 2) {
                FillResponse fillResponse = (FillResponse) fillService.fill(params[1], 4);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String jsonString = gson.toJson(fillResponse);
                writeString(jsonString, exchange.getResponseBody());
                exchange.getResponseBody().close();
            } else {
                FillResponse fillResponse = (FillResponse) fillService.fill(params[1], Integer.parseInt(params[2]));
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String jsonString = gson.toJson(fillResponse);
                writeString(jsonString, exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
        }catch(Exception e){
            ErrorMessage error = new ErrorMessage(e.getMessage());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            String jsonString = gson.toJson(error);
            writeString(jsonString, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }

    }
    public void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


}
