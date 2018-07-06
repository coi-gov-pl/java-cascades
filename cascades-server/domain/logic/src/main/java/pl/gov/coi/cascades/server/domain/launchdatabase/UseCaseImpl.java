package pl.gov.coi.cascades.server.domain.launchdatabase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstance.DatabaseInstanceBuilder;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.User;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

@Builder
@AllArgsConstructor
public class UseCaseImpl implements UseCase {

    private final LaunchNewDatabaseGatewayFacade launchNewDatabaseGatewayFacade;
    private final DatabaseNameGeneratorService databaseNameGeneratorService;
    private final UsernameAndPasswordCredentialsGeneratorService credentialsGeneratorService;
    private final DatabaseTypeClassNameService databaseTypeClassNameService;
    private final DatabaseIdGeneratorService databaseIdGeneratorService;

    /**
     * This method takes a pair of request and response objects. That ensures decoupling of presentation from domain.
     *
     * @param request Given request of launching new database instance.
     * @param response Given response of launching new database instance.
     */
    @Override
    public void execute(Request request, Response response) {
        Optional<User> user = request.getUser() != null
            ? launchNewDatabaseGatewayFacade.findUser(request.getUser().getUsername())
            : Optional.empty();

        Validator.ValidatorBuilder validatorBuilder = Validator.builder()
            .databaseLimitGateway(launchNewDatabaseGatewayFacade.getDatabaseLimitGateway())
            .templateIdGateway(launchNewDatabaseGatewayFacade.getTemplateIdGateway())
            .request(request)
            .response(response);

        user.ifPresent(validatorBuilder::user);

        Validator validator = validatorBuilder.build();
        if (validator.validate()) {
            succeedResponse(request, response, validator);
        }
    }

    private void succeedResponse(Request request,
                                 Response response,
                                 Validator validator) {
        String databaseName = generateDatabaseName(request, databaseNameGeneratorService);

        UsernameAndPasswordCredentials credentials = credentialsGeneratorService.generate();
        DatabaseId newId = databaseIdGeneratorService.generate();

        DatabaseInstanceBuilder candidateBuilder = DatabaseInstance.builder()
            .databaseId(newId)
            .databaseName(databaseName)
            .databaseType(validator.getDatabaseType())
            .created(Date.from(Instant.now()))
            .credentials(credentials)
            .reuseTimes(0)
            .template(validator.getTemplateId())
            .status(DatabaseStatus.LAUNCHED);
        request.getInstanceName()
            .ifPresent(candidateBuilder::instanceName);
        DatabaseInstance candidate = candidateBuilder.build();

        DatabaseInstance launchedDatabaseInstance = launchNewDatabaseGatewayFacade.launchDatabase(candidate);
        User user = validator.getUser();
        user = user.addDatabaseInstance(launchedDatabaseInstance);
        launchNewDatabaseGatewayFacade.save(user);

        response.setDatabaseId(launchedDatabaseInstance.getDatabaseId());
        response.setNetworkBind(launchedDatabaseInstance.getNetworkBind());
        response.setCredentials(launchedDatabaseInstance.getCredentials());
        response.setDatabaseName(launchedDatabaseInstance.getDatabaseName());
    }

    private static String generateDatabaseName(Request request,
                                               DatabaseNameGeneratorService databaseNameGeneratorService) {
        Optional<String> instanceName = request.getInstanceName();
        return instanceName.isPresent()
            ? databaseNameGeneratorService.generate(instanceName.get())
            : databaseNameGeneratorService.generate();
    }

}
