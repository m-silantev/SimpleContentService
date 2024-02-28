package tech.silantev.simple.cdn;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {
    public void start() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(9999), 0);
        server.createContext("/", new Handler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }


}
