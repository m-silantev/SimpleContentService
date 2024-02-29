package tech.silantev.simple.cdn;

import java.io.FileInputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = loadProperties();
        new App(properties).start();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("application.properties")) {
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}