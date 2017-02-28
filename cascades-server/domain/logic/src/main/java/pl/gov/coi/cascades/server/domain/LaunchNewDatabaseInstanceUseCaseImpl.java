package pl.gov.coi.cascades.server.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.TemplateId;
import pl.gov.coi.cascades.contract.domain.UsernameAndPasswordCredentials;
import pl.gov.coi.cascades.server.domain.LaunchNewDatabaseInstanceValidator.LaunchNewDatabaseInstanceValidatorBuilder;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

@Builder
@RequiredArgsConstructor
class LaunchNewDatabaseInstanceUseCaseImpl implements LaunchNewDatabaseInstanceUseCase {

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
     * @param response Given response of launching new darabase instance.
     */
    @Override
    public void execute(LaunchNewDatabaseInstanceRequest request, LaunchNewDatabaseInstanceResponse response) {
        Optional<TemplateId> templateId = templateIdGateway.find(request.getTemplateId().orElse(null));
        Optional<User> user = userGateway.find(request.getUser().getUsername());
        DatabaseTypeDTO databaseTypeDTO = databaseTypeClassNameService.getDatabaseType(request.getTypeClassName());

        LaunchNewDatabaseInstanceValidatorBuilder validatorBuilder = LaunchNewDatabaseInstanceValidator.builder()
            .databaseLimitGateway(databaseLimitGateway)
            .request(request)
            .response(response)
            .databaseTypeDTO(databaseTypeDTO);

        templateId.ifPresent(validatorBuilder::templateId);
        user.ifPresent(validatorBuilder::user);

        LaunchNewDatabaseInstanceValidator validator = validatorBuilder.build();
        if (validator.validate()) {
            succeedResponse(request, response, validator);
        }
    }

    private void succeedResponse(LaunchNewDatabaseInstanceRequest request,
                                 LaunchNewDatabaseInstanceResponse response,
                                 LaunchNewDatabaseInstanceValidator validator) {
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

    private DatabaseId generateInstanceName(LaunchNewDatabaseInstanceRequest request,
                                            DatabaseIdGeneratorService databaseIdGeneratorService) {
        Optional<String> instanceName = request.getInstanceName();
        return instanceName.isPresent()
            ? databaseIdGeneratorService.generate(instanceName.get())
            : databaseIdGeneratorService.generate();
    }

}
