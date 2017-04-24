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
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateId;
import pl.gov.coi.cascades.server.persistance.hibernate.entity.TemplateIdStatus;
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

    private static final String EMAIL = "jrambo@example.org";
    private static final String USERNAME = "jrambo";
    private static final String ID = "12345678";
    private static final String PASSWORD = "12345678";
    private static final String HOST = "db01.lab.internal";
    private static final int PORT = 5432;
    private static final Long DATABASE_ID_AS_LONG = 45L;
    private static final String DATABASE_TYPE = "stub";
    private static final String INSTANCE_NAME = "ora12e34";
    private static final String DATABASE_NAME = "oracle 12c";
    private static final String SERVER_ID = "5v36y5646";
    private static final long TEMPLATE_ID = 8958395489L;
    private static final String TEMPLATE_ID_NAME = "oracle_template";
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
            USERNAME,
            ID,
            EMAIL,
            databases
        );

        // when
        User actual = userMapper.toHibernateEntity(user);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(USERNAME);
        assertThat(actual.getEmail()).isEqualTo(EMAIL);
        assertThat(actual.getId()).isEqualTo(Long.parseLong(ID));
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
        credentials.setPassword(PASSWORD);
        credentials.setUsername(USERNAME);
        NetworkBind networkBind = new NetworkBind();
        networkBind.setHost(HOST);
        networkBind.setPort(PORT);
        pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance hibernateInstance
            = new pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseInstance();
        hibernateInstance.setId(DATABASE_ID_AS_LONG);
        TemplateId templateId = new TemplateId();
        templateId.setDefault(false);
        templateId.setServerId(SERVER_ID);
        templateId.setName(TEMPLATE_ID_NAME);
        templateId.setStatus(TemplateIdStatus.CREATED);
        hibernateInstance.setTemplateId(templateId);
        hibernateInstance.setType(DATABASE_TYPE);
        hibernateInstance.setInstanceName(INSTANCE_NAME);
        hibernateInstance.setReuseTimes(1);
        hibernateInstance.setDatabaseName(DATABASE_NAME);
        hibernateInstance.setCredentials(credentials);
        hibernateInstance.setNetworkBind(networkBind);
        hibernateInstance.setStatus(pl.gov.coi.cascades.server.persistance.hibernate.entity.DatabaseStatus.LAUNCHED);
        hibernateInstance.setCreated(created);
        databases.add(hibernateInstance);
        UserMapper userMapper = new UserMapper(
            databaseTypeClassNameService
        );
        User user = new User();
        user.setEmail(EMAIL);
        user.setUsername(USERNAME);
        user.setId(Long.parseLong(ID));
        user.setDatabases(databases);

        // when
        pl.gov.coi.cascades.server.domain.User actual = userMapper.fromHibernateEntity(user);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getEmail()).isEqualTo(EMAIL);
        assertThat(actual.getId()).isEqualTo(ID);
        assertThat(actual.getUsername()).isEqualTo(USERNAME);
        assertThat(actual.getDatabases()).hasSize(1);
    }

}
