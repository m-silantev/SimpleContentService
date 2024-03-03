package tech.silantev.simple.cdn.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Loader {

    private final java.util.Properties properties = new java.util.Properties();

    public Loader(Path path, String defaultProperties) throws IOException {
        if (!Files.exists(path)) {
            Files.write(path, defaultProperties.getBytes());
        }
        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            properties.load(fis);
        }
    }

    public Properties getFile() {
        return properties;
    }
}
