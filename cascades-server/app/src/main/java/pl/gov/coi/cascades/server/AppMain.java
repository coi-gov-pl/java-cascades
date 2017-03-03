package pl.gov.coi.cascades.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AppMain {
    private static AppMain instance;
    private ConfigurableApplicationContext ctx;

    public static void main(String[] args) throws Exception {
        instance = new AppMain();
        instance.start(args);
    }

    public static void stop() {
        instance.close();
    }

    private void start(String[] args) {
        ctx = SpringApplication.run(AppMain.class, args);
    }

    private void close() {
        ctx.close();
    }
}
