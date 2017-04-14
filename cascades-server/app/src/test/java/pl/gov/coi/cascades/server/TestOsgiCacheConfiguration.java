package pl.gov.coi.cascades.server;

import org.apache.felix.framework.cache.BundleCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 11.04.17.
 */
@Configuration
@Profile(Environment.DEVELOPMENT_NAME)
class TestOsgiCacheConfiguration {

    @Bean
    OsgiFrameworkConfigurator produceAutoConfiguredOsgiFrameworkConfigurator() {
        return config -> {
            config.put(BundleCache.CACHE_ROOTDIR_PROP, createRandomPath("target").toString());
        };
    }

    private Path createRandomPath(String rootDir) {
        return Paths.get(rootDir)
            .resolve("osgi-context")
            .resolve(createRandomDirectoryName());
    }

    private String createRandomDirectoryName() {
        return Long.toHexString(
            ThreadLocalRandom.current().nextLong()
        );
    }

}
