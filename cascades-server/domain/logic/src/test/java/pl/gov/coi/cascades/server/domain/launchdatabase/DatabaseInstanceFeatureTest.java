package pl.gov.coi.cascades.server.domain.launchdatabase;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.02.17.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features/LaunchNewDatabaseInstance.feature",
    plugin = "pretty"
)
public class DatabaseInstanceFeatureTest {

}
