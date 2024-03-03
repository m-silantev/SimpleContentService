package tech.silantev.simple.cdn;

import com.sun.net.httpserver.HttpServer;
import tech.silantev.simple.cdn.properties.Properties;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    private final Properties properties;

    public App(Properties properties) {
        this.properties = properties;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(properties.getPort()), 0);
        server.createContext("/", new Handler(properties));
        server.setExecutor(null);
        server.start();
    }

}
