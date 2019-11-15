package Handlers;


import Request.PersonRequest;
import Response.*;
import Services.Person;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Arrays;

public class PersonRequestHandler implements HttpHandler {
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
                    Person personService = new Person();
                    PersonsResponse personsResponse = (PersonsResponse) personService.persons(authToken);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonString = gson.toJson(personsResponse);
                    writeString(jsonString, exchange.getResponseBody());
                    exchange.getResponseBody().close();
                } else if (params.length == 2) {
                    Person personService = new Person();
                    PersonResponse personResponse = (PersonResponse) personService.person(params[1], authToken);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonString = gson.toJson(personResponse);
                    writeString(jsonString, exchange.getResponseBody());
                    exchange.getResponseBody().close();
                } else {
                    ErrorMessage error = new ErrorMessage("Invalid number of parameters error");
                    String jsonString = gson.toJson(error);
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
