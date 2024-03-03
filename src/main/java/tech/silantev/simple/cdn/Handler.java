package tech.silantev.simple.cdn;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class Handler implements HttpHandler {

    private final Duration pause;

    public Handler(Properties properties) {
        this.pause = properties.getPause();
    }

    @Override
    public void handle(HttpExchange t) {
        try {
            Path path = Paths.get(t.getRequestURI().getPath()).getName(0);
            byte[] bytes = Files.readAllBytes(path);
            t.sendResponseHeaders(200, bytes.length);
            Thread.sleep(pause.toMillis());
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


