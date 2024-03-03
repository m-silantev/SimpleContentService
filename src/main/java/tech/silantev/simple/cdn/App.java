package tech.silantev.simple.cdn;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {

    private final Properties properties;

    public App(Properties properties) {
        this.properties = properties;
    }

    public void start() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(properties.getPort()), 0);
        server.createContext("/", new Handler(properties));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

}
