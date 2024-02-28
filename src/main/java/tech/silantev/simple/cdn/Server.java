package tech.silantev.simple.cdn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {

    public void listen(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            System.out.println("Now serving at <http://localhost>:" + port);
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(() -> handleClientRequest(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequest(Socket clientSocket) {
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
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractTargetUrl(String request) {
        String[] lines = request.split("\\r\\n");
        String[] requestLine = lines[0].split(" ");
        return requestLine[1];
    }
}


