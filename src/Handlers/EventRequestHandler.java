package Handlers;


import Request.PersonRequest;
import Response.*;
import Services.Event;
import Services.Person;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.Arrays;

public class EventRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if(exchange.getRequestHeaders().containsKey("Authorization")) {
                String authToken = exchange.getRequestHeaders().getFirst("Authorization");
                String urlPath = exchange.getRequestURI().toString();
                String params[] = urlPath.split("/");
                params = Arrays.copyOfRange(params, 1, params.length);
                Gson gson = new Gson();
                if (params.length == 1) {
                    Event eventService = new Event();
                    EventsResponse eventsResponse = (EventsResponse) eventService.events(authToken);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonString = gson.toJson(eventsResponse);
                    writeString(jsonString, exchange.getResponseBody());
                    exchange.getResponseBody().close();
                } else if (params.length == 2) {
                    Event eventService = new Event();
                    EventResponse eventResponse = (EventResponse) eventService.event(params[1], authToken);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonString = gson.toJson(eventResponse);
                    writeString(jsonString, exchange.getResponseBody());
                    exchange.getResponseBody().close();
                } else {
                    ErrorMessage error = new ErrorMessage("Invalid number of parameters error");
                    String jsonString = gson.toJson(error);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    writeString(jsonString, exchange.getResponseBody());
                    exchange.getResponseBody().close();
                }
            }
            else{
                throw new Exception("Not authorized error");
            }
        }catch(Exception e){
            ErrorMessage error = new ErrorMessage(e.getMessage());
            Gson gson = new Gson();
            String jsonString = gson.toJson(error);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
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
