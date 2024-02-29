package tech.silantev.simple.cdn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = loadProperties();
        new App(properties).start();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        File file = new File("application.properties");
        if (!file.exists()) {
            try {
                file.createNewFile();
                Files.write(file.toPath(), "pauseMs=500\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}