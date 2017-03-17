package pl.gov.coi.cascades.server;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 16.03.17
 */
@Component
public class SystemBundleActivator implements BundleActivator {
    @Override
    public void start(BundleContext context) throws Exception {
        // do nothing
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        // do nothing
    }
}
