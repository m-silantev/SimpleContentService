package tech.silantev.simple.cdn.properties;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Properties {

    private final int port;
    private final Duration pause;

    public Properties(Path path) throws IOException {
        this(new Loader(path, Property.generateDefaultFileContent()).getFile());
    }

    public Properties(java.util.Properties properties) {
        this.port = Property.PORT.asInt(properties);
        this.pause = Duration.ofMillis(Property.PAUSE.asInt(properties));
    }

    public int getPort() {
        return port;
    }

    public Duration getPause() {
        return pause;
    }

    enum Property {

        PORT("port", "8081"),
        PAUSE("pauseMs", "500"),
        ;

        private final String nameInFile;
        private final String defaultValue;

        Property(String nameInFile, String defaultValue) {
            this.nameInFile = nameInFile;
            this.defaultValue = defaultValue;
        }

        public static String generateDefaultFileContent() {
            return Arrays.stream(values())
                    .map(name -> name.nameInFile + "=" + name.defaultValue + "\n")
                    .collect(Collectors.joining());
        }

        public int asInt(java.util.Properties properties) {
            String fromFile = properties.getProperty(nameInFile);
            return Integer.parseInt(fromFile == null || fromFile.isEmpty() ? defaultValue : fromFile);
        }
    }
}
