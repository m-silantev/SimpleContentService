package tech.silantev.simple.cdn;

import java.time.Duration;

public class Properties {

    private final int port;
    private final Duration pause;

    public Properties(java.util.Properties properties) {
        this.port = Integer.parseInt(properties.getProperty("port"));
        this.pause = Duration.ofMillis(Long.parseLong(properties.getProperty("pauseMs")));
    }

    public int getPort() {
        return port;
    }

    public Duration getPause() {
        return pause;
    }
}
