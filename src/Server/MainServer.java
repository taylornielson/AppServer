package Server;
import DAO.DataAccessException;
import DAO.Database;
import Handlers.*;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;


public class MainServer {
    public static void main(String args[]) throws IOException, DataAccessException, SQLException {
        if (args.length < 1) {
            System.out.println("More arguments needed. Please specify the port number");
        }
        //int port = Integer.parseInt(args[0]);
        startServer(8080);
    }

    public static void startServer(int port) throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(serverAddress, 10);
        registerHandlers(server);
        server.start();
        System.out.println("FamilyMapServer listening on port " + port);
    }


    private static void registerHandlers(HttpServer server) {
        server.createContext("/", new FileRequestHandler()); //Done
        server.createContext("/user/register", new RegisterRequestHandler()); //Done
        server.createContext("/user/login", new LoginRequestHandler()); //Done
        server.createContext("/clear", new ClearRequestHandler()); //Done
        server.createContext("/fill", new FillRequestHandler()); //Done
        server.createContext("/load", new LoadRequestHandler()); //Done
        server.createContext("/person", new PersonRequestHandler());
        server.createContext("/event", new EventRequestHandler());
    }

}


