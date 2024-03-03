package tech.silantev.simple.cdn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = loadProperties();
        new App(properties).start();
    }

    private static Properties loadProperties() {
        java.util.Properties util = new java.util.Properties();
        File file = new File("application.properties");
        if (!file.exists()) {
            try {
                file.createNewFile();
                String defaultProperties = "pauseMs=500\n" +
                        "port=8081\n";
                Files.write(file.toPath(), defaultProperties.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            util.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Properties(util);
    }
}