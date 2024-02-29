package tech.silantev.simple.cdn;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.Properties;

public class App {

    private final Properties properties;

    public App(Properties properties) {
        this.properties = properties;
    }

    public void start() throws Exception {
        int port = Integer.parseInt(Optional.ofNullable(properties.getProperty("port")).orElse("8081"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new Handler(properties));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

}
