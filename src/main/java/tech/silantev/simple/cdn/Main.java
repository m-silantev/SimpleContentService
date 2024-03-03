package tech.silantev.simple.cdn;

import tech.silantev.simple.cdn.properties.Properties;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties(Paths.get("application.properties"));
        new App(properties).start();
    }
}