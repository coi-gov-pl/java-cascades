package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.contract.domain.ConnectionStringProducer;
import pl.gov.coi.cascades.contract.domain.DatabaseType;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.DatabaseTypeDTO;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Credentials;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.NetworkBind;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseIdGatewayStub;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.03.17.
 */
public class DatabaseInstanceMapperTest {

    private static final String password = "12345678";
    private static final String username = "Ben Affleck";
    private static final String host = "db01.lab.internal";
    private static final int port = 5432;
    private static final String databaseId = "19";
    private static final Long databaseIdAsLong = 45L;
    private static final String templateId = "oracle";
    private static final String databaseType = "stub";
    private static final String instanceName = "ora12e34";
    private static final String databaseName = "oracle 12c";
    private Date created = Date.from(Instant.now());

    @Mock
    private DatabaseTypeClassNameService databaseTypeClassNameService;

    @Mock
    private DatabaseTypeDTO databaseTypeDTO;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testToHibernateEntity() throws Exception {
        // given
        DatabaseInstanceMapper databaseInstanceMapper = new DatabaseInstanceMapper(databaseTypeClassNameService);

        // when
        DatabaseInstance actual = databaseInstanceMapper.toHibernateEntity(DatabaseIdGatewayStub.INSTANCE1);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(databaseIdAsLong);
        assertThat(actual.getTemplateId()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getTemplateId().getId());
        assertThat(actual.getType()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getDatabaseType().getName());
        assertThat(actual.getInstanceName()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getInstanceName());
        assertThat(actual.getReuseTimes()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getReuseTimes());
        assertThat(actual.getDatabaseName()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getDatabaseName());
        assertThat(actual.getCredentials().getPassword())
            .isEqualTo(Arrays.toString(DatabaseIdGatewayStub.INSTANCE1.getCredentials().getPassword()));
        assertThat(actual.getCredentials().getUsername()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getCredentials().getUsername());
        assertThat(actual.getNetworkBind().getHost()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getNetworkBind().getHost());
        assertThat(actual.getNetworkBind().getPort()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getNetworkBind().getPort());
        assertThat(actual.getStatus().name()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getStatus().name());
        assertThat(actual.getCreated()).isEqualTo(DatabaseIdGatewayStub.INSTANCE1.getCreated());
    }

    @Test
    public void testFromHibernateEntity() throws Exception {
        // given
        when(databaseTypeClassNameService.getDatabaseType(anyString())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTO);
        doNothing().when(databaseTypeDTO).resolve();
        DatabaseInstanceMapper databaseInstanceMapper = new DatabaseInstanceMapper(databaseTypeClassNameService);
        Credentials credentials = new Credentials();
        credentials.setPassword(password);
        credentials.setUsername(username);
        NetworkBind networkBind = new NetworkBind();
        networkBind.setHost(host);
        networkBind.setPort(port);
        pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance hibernateInstance
            = new pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance();
        hibernateInstance.setId(databaseIdAsLong);
        hibernateInstance.setTemplateId(templateId);
        hibernateInstance.setType(databaseType);
        hibernateInstance.setInstanceName(instanceName);
        hibernateInstance.setReuseTimes(1);
        hibernateInstance.setDatabaseName(databaseName);
        hibernateInstance.setCredentials(credentials);
        hibernateInstance.setNetworkBind(networkBind);
        hibernateInstance.setStatus(pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus.LAUNCHED);
        hibernateInstance.setCreated(created);

        // when
        pl.gov.coi.cascades.server.domain.DatabaseInstance actual = databaseInstanceMapper.fromHibernateEntity(hibernateInstance);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getDatabaseId().getId()).isEqualTo(databaseId);
        assertThat(actual.getTemplateId().getId()).isEqualTo(templateId);
        assertThat(actual.getDatabaseType()).isEqualTo(null);
        assertThat(actual.getInstanceName()).isEqualTo(instanceName);
        assertThat(actual.getReuseTimes()).isEqualTo(1);
        assertThat(actual.getDatabaseName()).isEqualTo(databaseName);
        assertThat(actual.getCredentials().getPassword()).isEqualTo(password.toCharArray());
        assertThat(actual.getCredentials().getUsername()).isEqualTo(username);
        assertThat(actual.getNetworkBind().getHost()).isEqualTo(host);
        assertThat(actual.getNetworkBind().getPort()).isEqualTo(port);
        assertThat(actual.getStatus()).isEqualTo(DatabaseStatus.LAUNCHED);
        assertThat(actual.getCreated()).isEqualTo(created);
    }

    @Test
    public void testDatabaseIdMapping() {
        // given
        Long id = -2174612783412L;
        Credentials credentials = new Credentials();
        credentials.setPassword(password);
        credentials.setUsername(username);
        NetworkBind networkBind = new NetworkBind();
        networkBind.setHost(host);
        networkBind.setPort(port);
        pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance hibernateInstance
            = new pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance();
        hibernateInstance.setId(databaseIdAsLong);
        hibernateInstance.setTemplateId(templateId);
        hibernateInstance.setType(databaseType);
        hibernateInstance.setInstanceName(instanceName);
        hibernateInstance.setReuseTimes(1);
        hibernateInstance.setDatabaseName(databaseName);
        hibernateInstance.setCredentials(credentials);
        hibernateInstance.setNetworkBind(networkBind);
        hibernateInstance.setStatus(pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus.LAUNCHED);
        hibernateInstance.setCreated(created);
        hibernateInstance.setId(id);
        DatabaseInstanceMapper databaseInstanceMapper = new DatabaseInstanceMapper(databaseTypeClassNameService);
        when(databaseTypeClassNameService.getDatabaseType(anyString()))
            .thenReturn(new DatabaseTypeDTOStub(databaseType));

        // when
        pl.gov.coi.cascades.server.domain.DatabaseInstance model =
            databaseInstanceMapper.fromHibernateEntity(hibernateInstance);
        DatabaseInstance mapped = databaseInstanceMapper.toHibernateEntity(model);

        // then
        assertThat(mapped).isNotSameAs(hibernateInstance);
        assertThat(mapped.getId()).isEqualTo(id);
        assertThat(model.getDatabaseId().getId()).isEqualTo("-rr04ayic");
    }

    private static final class DatabaseTypeDTOStub extends DatabaseTypeDTO {
        private DatabaseTypeDTOStub(String type) {
            super(new DatabaseType() {

                @Override
                public String getName() {
                    return type;
                }

                @Override
                public ConnectionStringProducer getConnectionStringProducer() {
                    return null;
                }
            });
        }
    }

}
