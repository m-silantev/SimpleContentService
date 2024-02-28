package tech.silantev.simple.cdn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleProxyServer {

    private static final int PORT = 9999;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT);) {
            System.out.println("Now serving at <http://localhost>:" + PORT);

            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(() -> handleClientRequest(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientRequest(Socket clientSocket) {
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

            // Open a connection to the target server
            Socket targetSocket = new Socket(targetUrl, 80);
            InputStream targetIn = targetSocket.getInputStream();
            OutputStream targetOut = targetSocket.getOutputStream();

            // Forward the client's request to the target server
            targetOut.write(request.getBytes());
            targetOut.flush();

            // Forward the target server's response to the client
            while ((bytesRead = targetIn.read(buffer)) != -1) {
                clientOut.write(buffer, 0, bytesRead);
                clientOut.flush();
            }

            // Close the sockets
            targetSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractTargetUrl(String request) {
        String[] lines = request.split("\\r\\n");
        String[] requestLine = lines[0].split(" ");
        return requestLine[1];
    }
}
