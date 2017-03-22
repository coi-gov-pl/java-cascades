package pl.gov.coi.cascades.server;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 17.03.17
 */
public class AppMainTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private SpringApplicationFactory applicationFactory;

    @Mock
    private SpringApplication application;

    @Mock
    private ConfigurableApplicationContext applicationContext;

    @Test
    public void testCreateDefault() {
        // given
        AppMain appMain = new AppMain();

        // then
        assertThat(appMain).isNotNull()
            .extracting(AppMain::getApplicationFactory)
            .isNotNull();
    }

    @Test
    public void testStart() {
        // given
        AppMain appMain = new AppMain(applicationFactory);
        when(applicationFactory.create(any()))
            .thenReturn(application);
        when(application.run(anyVararg()))
            .thenReturn(applicationContext);

        // when
        appMain.start(new String[0]);
        ConfigurableApplicationContext ctx = appMain.getCtx();

        // then
        assertThat(ctx).isNotNull()
            .isSameAs(applicationContext);
    }

    @Test
    public void testMain() throws Exception {
        try {
            // when
            AppMain.setAppMainSupplier(() -> new AppMain(applicationFactory));
            when(applicationFactory.create(any()))
                .thenReturn(application);
            when(application.run(anyVararg()))
                .thenReturn(applicationContext);
            when(applicationContext.isActive()).thenReturn(true);
            AppMain.main(new String[0]);

            // then
            assertThat(AppMain.isRunning())
                .isTrue();

            // when
            doReturn(false).when(applicationContext).isActive();
            AppMain.stop();

            // then
            assertThat(AppMain.isRunning())
                .isFalse();

        } finally {
            AppMain.setAppMainSupplier(AppMain.DEFAULT_APP_MAIN_SUPPLIER);
        }
    }

    @Test
    public void testDefaultSupplier() {
        // when
        AppMain main = AppMain.DEFAULT_APP_MAIN_SUPPLIER.get();

        // then
        assertThat(main).isNotNull();
    }

}
