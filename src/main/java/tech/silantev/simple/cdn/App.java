package tech.silantev.simple.cdn;

public class App {
    public void start() {
        Server server = new Server();
        server.listen(9999);
    }
}
