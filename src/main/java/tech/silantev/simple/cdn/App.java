package tech.silantev.simple.cdn;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.SimpleFileServer;
import tech.silantev.simple.cdn.properties.Properties;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Paths;

public class App {

    private final Properties properties;

    public App(Properties properties) {
        this.properties = properties;
    }

    public void start() throws IOException {
        HttpServer server = SimpleFileServer.createFileServer(new InetSocketAddress(properties.getPort()),
                Paths.get(""), SimpleFileServer.OutputLevel.INFO);
        server.createContext("/", new Handler(properties));
        server.setExecutor(null);
        server.start();
    }

}
