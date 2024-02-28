package tech.silantev.simple.cdn;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange t) {
        try {
            URI requestURI = t.getRequestURI();
            Path path = Paths.get(requestURI.getPath()).getName(0);
            byte[] bytes = Files.readAllBytes(path);
            t.sendResponseHeaders(200, bytes.length);
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequest(Socket clientSocket) throws IOException {

        try {
            InputStream clientIn = clientSocket.getInputStream();
            OutputStream clientOut = clientSocket.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;

            // Read the client's request
            bytesRead = clientIn.read(buffer);
            String request = new String(buffer, 0, bytesRead);

            // Extract the target URL from the request
            String targetUrl = extractTargetUrl(request);

            Path path = Paths.get(targetUrl);

            byte[] bytes = Files.readAllBytes(path);
            clientOut.write(bytes);
            clientOut.flush();

            // Close the sockets
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clientSocket.close();
        }
    }

    private String extractTargetUrl(String request) {
        String[] lines = request.split("\\r\\n");
        String[] requestLine = lines[0].split(" ");
        return requestLine[1].replace("/", "./");
    }
}


