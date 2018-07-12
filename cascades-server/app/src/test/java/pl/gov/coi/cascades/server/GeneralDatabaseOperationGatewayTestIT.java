package pl.gov.coi.cascades.server;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.DatabaseTypeImpl;
import pl.gov.coi.cascades.server.domain.launchdatabase.UsernameAndPasswordCredentialsImpl;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author <a href="mailto:lukasz.malek@coi.gov.pl">Łukasz Małek</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ProductionHibernateTest
public class GeneralDatabaseOperationGatewayTestIT {

    private static final String TEMPLATE_NAME = "templateNameExample";
    private static final String SERVER_ID = "asdq3";
    private static final String ID_DATABASE = "a123xqw2";
    private static final String DATABASE_NAME = "exampleDatabaseName";
    private static final String MY_DATABASE = "my_database";
    private static final String FILE_NAME = "tempFile.txt";
    private static final String USERNAME = "exampleUsername";
    private static final String PASSWORD = "examplePassword";
    private static final String ORACLE = "ora12c";

    private Template template;
    private ServerConfigurationService serverConfigurationService;
    private DatabaseManager databaseManager;
    private GeneralTemplateGateway generalTemplateGateway;
    private GeneralDatabaseOperationGateway generalDatabaseOperationGateway;
    private GeneralUserGateway generalUserGateway;
    private DatabaseInstance databaseInstance;


    @Inject
    public void setServerConfigurationService(ServerConfigurationService serverConfigurationService) {
        this.serverConfigurationService = serverConfigurationService;
    }

    @Inject
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void init() {
        generalUserGateway = new GeneralUserGateway(databaseManager);
        generalTemplateGateway = new GeneralTemplateGateway(databaseManager);
        generalDatabaseOperationGateway = new GeneralDatabaseOperationGateway(serverConfigurationService, databaseManager);
        databaseInstance = createDatabaseInstance(template, ORACLE);
        template = Template.builder()
            .name(TEMPLATE_NAME)
            .serverId(SERVER_ID)
            .isDefault(false)
            .status(TemplateIdStatus.CREATED)
            .build();
    }

    @After
    public void afterTest() {
        generalTemplateGateway.deleteTemplate(template);
        // TODO: write implementation - remove database
        generalUserGateway.deleteUser(databaseInstance);
    }

    //Ignored test because Travis does not have an integrated database. Only for local test.
    @Ignore
    @Test
    public void shouldCreateDatabaseOracle() throws IOException {
        //given
        File tempFile = tempFolder.newFile(FILE_NAME);
        FileUtils.writeStringToFile(tempFile, "CREATE TABLE employeess (\n" +
            "  emp_id      number(38) unique not null,\n" +
            "  name        varchar2(32),\n" +
            "  department  number not null,\n" +
            "  hire_date   date not null);\n" +
            "\n");

        //when
        generalTemplateGateway.createTemplate(template, tempFile.toPath());
        generalDatabaseOperationGateway.createDatabase(databaseInstance);

        //then
        generalUserGateway.createUser(databaseInstance);
    }


    private DatabaseInstance createDatabaseInstance(Template template, String databaseType) {
        return DatabaseInstance.builder()
            .databaseId(new DatabaseId(ID_DATABASE))
            .status(DatabaseStatus.LAUNCHED)
            .created(new Date())
            .credentials(new UsernameAndPasswordCredentialsImpl(USERNAME, PASSWORD.toCharArray()))
            .databaseName(DATABASE_NAME)
            .databaseType(new DatabaseTypeImpl(databaseType))
            .instanceName(MY_DATABASE)
            .networkBind(null)
            .reuseTimes(1)
            .template(template)
            .build();
    }
}
