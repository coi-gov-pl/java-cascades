package pl.gov.coi.cascades.client.configuration;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkState;

/**
 * This class holds main Guice's {@link Injector} instance. One should register modules
 * with {@link #register(Module)} before using {@link #getBean(Class)} method.
 *
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
public final class Container {
    public static final Container INSTANCE = new Container();
    private final List<Module> modules = new ArrayList<>();
    @Nullable
    private Injector injector;

    private Container() {
        // do nothing
    }

    /**
     * Registers a Guice {@link Module} to be used in injector.
     *
     * This method MUST be executed before using {@link #getBean(Class)} method or exception
     * will be thrown.
     *
     * @param module a guice module
     */
    public Container register(Module module) {
        checkState(injector == null, "20170329:105518");
        modules.add(module);
        return this;
    }

    /**
     * Gets a Guice bean from DI container. Method {@link #register(Module)} must be
     * executed earlier.
     *
     * @param type a class that represents type of bean to fetch from DI container
     * @param <T> a type of bean to be fetched from DI container
     * @return a bean fetched from DI container
     */
    public <T> T getBean(Class<T> type) {
        return getInjector()
            .getInstance(type);
    }

    @VisibleForTesting
    void reset() {
        injector = null;
        modules.clear();
    }

    private Injector getInjector() {
        if (this.injector == null) {
            this.injector = Guice.createInjector(modules);
        }
        return this.injector;
    }
}
