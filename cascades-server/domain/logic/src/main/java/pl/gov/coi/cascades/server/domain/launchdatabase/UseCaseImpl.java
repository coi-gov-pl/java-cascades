package pl.gov.coi.cascades.server.domain.launchdatabase;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstance.DatabaseInstanceBuilder;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
import pl.gov.coi.cascades.server.domain.DatabaseStatus;
import pl.gov.coi.cascades.server.domain.DatabaseTypeClassNameService;
import pl.gov.coi.cascades.server.domain.DatabaseTypeDTO;
import pl.gov.coi.cascades.server.domain.TemplateIdGateway;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.UserGateway;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

@Builder
@RequiredArgsConstructor
public class UseCaseImpl implements UseCase {

    private final TemplateIdGateway templateIdGateway;
    private final DatabaseInstanceGateway databaseInstanceGateway;
    private final UserGateway userGateway;
    private final DatabaseLimitGateway databaseLimitGateway;
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
        Optional<TemplateId> templateId = templateIdGateway.find(request.getTemplateId().orElse(null));
        if (!templateId.isPresent()) {
            templateId = templateIdGateway.getDefaultTemplateId();
        }
        Optional<User> user = request.getUser() != null
            ? userGateway.find(request.getUser().getUsername())
            : Optional.empty();
        DatabaseTypeDTO databaseTypeDTO = databaseTypeClassNameService
            .getDatabaseType(request.getType());

        Validator.ValidatorBuilder validatorBuilder = Validator.builder()
            .databaseLimitGateway(databaseLimitGateway)
            .request(request)
            .response(response)
            .databaseTypeDTO(databaseTypeDTO);

        templateId.ifPresent(validatorBuilder::templateId);
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
            .templateId(validator.getTemplateId())
            .status(DatabaseStatus.LAUNCHED);
        request.getInstanceName()
            .ifPresent(candidateBuilder::instanceName);
        DatabaseInstance candidate = candidateBuilder.build();

        DatabaseInstance launchedDatabaseInstance = databaseInstanceGateway.launchDatabase(candidate);
        User user = validator.getUser();
        user = user.addDatabaseInstance(launchedDatabaseInstance);
        userGateway.save(user);

        response.setDatabaseId(launchedDatabaseInstance.getDatabaseId());
        response.setNetworkBind(launchedDatabaseInstance.getNetworkBind());
        response.setCredentials(launchedDatabaseInstance.getCredentials());
        response.setDatabaseName(launchedDatabaseInstance.getDatabaseName());
    }

    private String generateDatabaseName(Request request,
                                            DatabaseNameGeneratorService databaseNameGeneratorService) {
        Optional<String> instanceName = request.getInstanceName();
        return instanceName.isPresent()
            ? databaseNameGeneratorService.generate(instanceName.get())
            : databaseNameGeneratorService.generate();
    }

}
