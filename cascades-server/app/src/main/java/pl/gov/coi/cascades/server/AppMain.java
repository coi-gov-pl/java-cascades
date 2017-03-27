package pl.gov.coi.cascades.server;

import com.google.common.annotations.VisibleForTesting;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;
import java.util.function.Supplier;

@SpringBootApplication
public class AppMain {
    static final Supplier<AppMain> DEFAULT_APP_MAIN_SUPPLIER =
        new AppMainSupplier();
    private static Supplier<AppMain> appMainSupplier = AppMain.DEFAULT_APP_MAIN_SUPPLIER;
    private static final SpringApplicationFactory DEFAULT_APPLICATION_FACTORY =
        new SpringApplicationFactory();
    private static AppMain instance;
    @Getter(AccessLevel.PACKAGE)
    private ConfigurableApplicationContext ctx;
    @Getter(AccessLevel.PACKAGE)
    private final SpringApplicationFactory applicationFactory;

    AppMain() {
        this(DEFAULT_APPLICATION_FACTORY);
    }

    AppMain(SpringApplicationFactory applicationFactory) {
        this.applicationFactory = applicationFactory;
    }

    public static void main(String[] args) throws Exception {
        instance = appMainSupplier.get();
        instance.start(args);
    }

    @VisibleForTesting
    static void setAppMainSupplier(Supplier<AppMain> appMainSupplier) {
        AppMain.appMainSupplier = appMainSupplier;
    }

    @VisibleForTesting
    public static void stop() {
        Optional.ofNullable(instance)
            .ifPresent(appMain -> {
                appMain.close();
                instance = null;
            });
    }

    @VisibleForTesting
    public static boolean isRunning() {
        return Optional.ofNullable(
                Optional.ofNullable(instance)
                .orElseGet(appMainSupplier)
                .getCtx()
            )
            .map(ConfigurableApplicationContext::isActive)
            .orElse(false);
    }

    @VisibleForTesting
    void start(String[] args) {
        ctx = applicationFactory
            .create(this.getClass())
            .run(args);
    }

    private void close() {
        ctx.close();
    }

    private static final class AppMainSupplier implements Supplier<AppMain> {

        @Override
        public AppMain get() {
            return new AppMain();
        }
    }
}
