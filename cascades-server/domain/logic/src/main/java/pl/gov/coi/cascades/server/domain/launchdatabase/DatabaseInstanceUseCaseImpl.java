package pl.gov.coi.cascades.server.domain.launchdatabase;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.DatabaseInstance;
import pl.gov.coi.cascades.server.domain.DatabaseInstanceGateway;
import pl.gov.coi.cascades.server.domain.DatabaseLimitGateway;
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
public class DatabaseInstanceUseCaseImpl implements DatabaseInstanceUseCase {

    private final TemplateIdGateway templateIdGateway;
    private final DatabaseInstanceGateway databaseInstanceGateway;
    private final UserGateway userGateway;
    private final DatabaseLimitGateway databaseLimitGateway;
    private final DatabaseIdGeneratorService databaseIdGeneratorService;
    private final UsernameAndPasswordCredentialsGeneratorService credentialsGeneratorService;
    private final DatabaseTypeClassNameService databaseTypeClassNameService;

    /**
     * This method takes a pair of request and response objects. That ensures decoupling of presentation from domain.
     *
     * @param request Given request of launching new database instance.
     * @param response Given response of launching new database instance.
     */
    @Override
    public void execute(DatabaseInstanceRequest request, DatabaseInstanceResponse response) {
        Optional<TemplateId> templateId = templateIdGateway.find(request.getTemplateId().orElse(null));
        Optional<User> user = request.getUser() != null
            ? userGateway.find(request.getUser().getUsername())
            : Optional.empty();
        DatabaseTypeDTO databaseTypeDTO = databaseTypeClassNameService.getDatabaseType(request.getTypeClassName());

        DatabaseInstanceValidator.DatabaseInstanceValidatorBuilder validatorBuilder = DatabaseInstanceValidator.builder()
            .databaseLimitGateway(databaseLimitGateway)
            .request(request)
            .response(response)
            .databaseTypeDTO(databaseTypeDTO);

        templateId.ifPresent(validatorBuilder::templateId);
        user.ifPresent(validatorBuilder::user);

        DatabaseInstanceValidator validator = validatorBuilder.build();
        if (validator.validate()) {
            succeedResponse(request, response, validator);
        }
    }

    private void succeedResponse(DatabaseInstanceRequest request,
                                 DatabaseInstanceResponse response,
                                 DatabaseInstanceValidator validator) {
        DatabaseId databaseId = generateInstanceName(request, databaseIdGeneratorService);

        UsernameAndPasswordCredentials credentials = credentialsGeneratorService.generate();

        DatabaseInstance candidate = DatabaseInstance.builder()
            .databaseId(databaseId)
            .databaseName(databaseId.getId())  // TODO: Maybe in future it should be separate to database id?
            .databaseType(validator.getDatabaseType())
            .instanceName(databaseId.getId())
            .created(Date.from(Instant.now()))
            .credentials(credentials)
            .reuseTimes(0)
            .templateId(validator.getTemplateId())
            .build();

        DatabaseInstance launchedDatabaseInstance = databaseInstanceGateway.launchDatabase(candidate);
        User user = validator.getUser();
        user.addDatabaseInstance(launchedDatabaseInstance);
        userGateway.save(user);

        response.setDatabaseId(databaseId.getId());
    }

    private DatabaseId generateInstanceName(DatabaseInstanceRequest request,
                                            DatabaseIdGeneratorService databaseIdGeneratorService) {
        Optional<String> instanceName = request.getInstanceName();
        return instanceName.isPresent()
            ? databaseIdGeneratorService.generate(instanceName.get())
            : databaseIdGeneratorService.generate();
    }

}
