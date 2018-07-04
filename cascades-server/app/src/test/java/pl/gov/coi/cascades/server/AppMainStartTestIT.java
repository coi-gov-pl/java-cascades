package pl.gov.coi.cascades.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppMain.class)
@ProductionHibernateTest
public class AppMainStartTestIT {

    @Mock
    private SpringApplicationFactory applicationFactory;

    @Mock
    private SpringApplication application;

    @Mock
    private ConfigurableApplicationContext applicationContext;

    @Test
    public void shouldApplicationStartOnProductionProfile(){
        //given
        AppMain.setAppMainSupplier(() -> new AppMain(applicationFactory));

        when(applicationFactory.create(any())).thenReturn(application);
        when(application.run(anyVararg())).thenReturn(applicationContext);
        when(applicationContext.isActive()).thenReturn(true);

        // when
        try {
            AppMain.main(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
