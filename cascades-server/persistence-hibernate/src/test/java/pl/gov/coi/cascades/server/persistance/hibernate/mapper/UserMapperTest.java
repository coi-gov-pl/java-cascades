package pl.gov.coi.cascades.server.persistance.hibernate.mapper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.gov.coi.cascades.server.domain.DatabaseIdMapper;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.DatabaseTypeDTO;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.Credentials;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.NetworkBind;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.User;
import pl.gov.coi.cascades.server.persistance.stub.DatabaseIdGatewayStub;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.03.17.
 */
public class UserMapperTest {

    private String email = "jrambo@example.org";
    private String username = "jrambo";
    private String id = "12345678";
    private static final String password = "12345678";
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
    private DatabaseTypeDTO databaseTypeDTO;

    @Mock
    private DatabaseIdMapper databaseIdMapper;

    @Mock
    private DatabaseTypeClassNameService databaseTypeClassNameService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testToHibernateEntity() throws Exception {
        // given
        Collection<pl.gov.coi.cascades.server.domain.DatabaseInstance> databases = new HashSet<>();
        UserMapper userMapper = new UserMapper(
            databaseTypeClassNameService
        );
        databases.add(DatabaseIdGatewayStub.INSTANCE1);
        pl.gov.coi.cascades.server.domain.User user = new pl.gov.coi.cascades.server.domain.User(
            username,
            id,
            email,
            databases
        );

        // when
        User actual = userMapper.toHibernateEntity(user);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getId()).isEqualTo(Long.parseLong(id));
        assertThat(actual.getDatabases()).hasSize(1);
    }

    @Test
    public void testFromHibernateEntity() throws Exception {
        // given
        Set<DatabaseInstance> databases = new HashSet<>();
        when(databaseTypeClassNameService.getDatabaseType(anyString())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onFail(any())).thenReturn(databaseTypeDTO);
        when(databaseTypeDTO.onSuccess(any())).thenReturn(databaseTypeDTO);
        doNothing().when(databaseTypeDTO).resolve();
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
        databases.add(hibernateInstance);
        UserMapper userMapper = new UserMapper(
            databaseTypeClassNameService
        );
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setId(Long.parseLong(id));
        user.setDatabases(databases);

        // when
        pl.gov.coi.cascades.server.domain.User actual = userMapper.fromHibernateEntity(user);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getEmail()).isEqualTo(email);
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getDatabases()).hasSize(1);
    }

}
