package tech.silantev.simple.cdn;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tech.silantev.simple.cdn.properties.Properties;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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
    public void handle(HttpExchange t) throws IOException {
        try {
            Path path = Paths.get(t.getRequestURI().getPath()).getName(0);
            byte[] bytes = Files.readAllBytes(path);
            t.sendResponseHeaders(HttpURLConnection.HTTP_OK, bytes.length);
            sleep();
            try (OutputStream os = t.getResponseBody()) {
                os.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            t.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(pause.toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


