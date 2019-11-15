package Handlers;

import Request.RegisterRequest;
import Response.ErrorMessage;
import Response.RegisterResponse;
import Services.Register;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.HttpURLConnection;


public class RegisterRequestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        InputStream is = exchange.getRequestBody();
        String reqData = readString(is);
        RegisterRequest regRequest = gson.fromJson(reqData, RegisterRequest.class);
        Register registerService = new Register();
        RegisterResponse regResponse = null;
        try {
            regResponse = (RegisterResponse) registerService.register(regRequest);
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(e.getMessage());
            String jsonString = gson.toJson(error);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            writeString(jsonString, exchange.getResponseBody());
            exchange.getResponseBody().close();
            exchange.close();
        }
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        String jsonString = gson.toJson(regResponse);
        writeString(jsonString, exchange.getResponseBody());
        exchange.getResponseBody().close();
        exchange.close();
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
