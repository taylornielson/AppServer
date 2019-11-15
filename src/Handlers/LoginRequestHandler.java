package Handlers;

import Request.LoginRequest;

import Response.*;
import Services.Login;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Gson gson = new Gson();
            InputStream is = exchange.getRequestBody();
            String temp = readString(is);
            LoginRequest logRequest = gson.fromJson(temp, LoginRequest.class);
            Login loginService = new Login();
            Response logResponse = loginService.login(logRequest);
            String jsonString = gson.toJson(logResponse);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            writeString(jsonString, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }catch(Exception e){
            Gson gson = new Gson();
            ErrorMessage temp = new ErrorMessage(e.getMessage());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            String jsonString = gson.toJson(temp);
            writeString(jsonString, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }
    }
    public void writeString(String str, OutputStream os) throws IOException {
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
