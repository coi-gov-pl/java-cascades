package pl.gov.coi.cascades.junit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pl.gov.coi.cascades.contract.configuration.Driver;
import pl.gov.coi.cascades.contract.domain.*;
import pl.gov.coi.cascades.contract.domain.Template;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 29.03.17
 */
@Ignore
public class CascadesRuleTest {

    private long id;
    private String generatedId;
    private String serverId;
    private String version;
    private String name;

    @Before
    public void setUp() {
        id = 123L;
        generatedId = "gw45223";
        serverId = "898693";
        version = "0.0.1";
        name = "oracle_template";
    }

    private static final ConnectionStringProducer CONNECTION_STRING_PRODUCER =
        new ConnectionStringProducer() {
            @Override
            public String produce(NetworkBind bind, String databaseName) {
                return String.format(
                    "%s:%d/%s",
                    bind.getHost(),
                    bind.getPort(),
                    databaseName
                );
            }
        };

    private static final DatabaseType DATABASE_TYPE_STUB = new DatabaseType() {
        @Override
        public String getName() {
            return "stub";
        }

        @Override
        public ConnectionStringProducer getConnectionStringProducer() {
            return CONNECTION_STRING_PRODUCER;
        }
    };

    @Test
    public void testBuilder() {
        // given

        // when

        // then
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testBefore() {
        // given
        CascadesRule rule = CascadesRule.builder()
            .driver(new Driver(DATABASE_TYPE_STUB, new Template(
                id,
                generatedId,
                name,
                TemplateIdStatus.CREATED,
                false,
                serverId,
                version))
            )
            .instanceName("pesel-test")
            .build();

        // when
        rule.before();

        // then
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testAfter() {
        // given

        // when

        // then
        Assert.fail("Not yet implemented");
    }

}
