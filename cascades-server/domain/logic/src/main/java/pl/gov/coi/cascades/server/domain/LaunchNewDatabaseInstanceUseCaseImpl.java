package pl.gov.coi.cascades.server.domain;

import com.google.common.base.Optional;
import pl.gov.coi.cascades.contract.domain.DatabaseId;
import pl.gov.coi.cascades.contract.domain.TemplateId;

class LaunchNewDatabaseInstanceUseCaseImpl implements LaunchNewDatabaseInstanceUseCase {

    private final TemplateIdGateway templateIdGateway;
    private final DatabaseInstanceGateway databaseInstanceGateway;
    private final UserGateway userGateway;

    public LaunchNewDatabaseInstanceUseCaseImpl(TemplateIdGateway templateIdGateway,
                                                DatabaseInstanceGateway databaseInstanceGateway,
                                                UserGateway userGateway) {
        this.templateIdGateway = templateIdGateway;
        this.databaseInstanceGateway = databaseInstanceGateway;
        this.userGateway = userGateway;
    }

    /**
     * This method takes a pair of request and response objects. That ensures decoupling of presentation from domain.
     *
     * @param request Given request of launching new database instance.
     * @param response Given response of launching new darabase instance.
     */
    @Override
    public void execute(LaunchNewDatabaseInstanceRequest request, LaunchNewDatabaseInstanceResponse response) {
        Optional<TemplateId> templateId = templateIdGateway.find(request.getTemplateId().orNull());
        Optional<User> user = userGateway.find(request.getUser().getUsername());

        DatabaseTypeClassNameService databaseTypeClassNameService = new DatabaseTypeClassNameService();
        DatabaseTypeDTO databaseTypeDTO = databaseTypeClassNameService.getDatabaseType(request.getTypeClassName());

        this.databaseType = null;
        databaseTypeDTO
            .onSuccess((databaseTypeConsumer) -> databaseType = databaseTypeConsumer)
            .onFail((errorConsumer) -> error = errorConsumer)
            .resolve();

        if (request.getTypeClassName() != null) {
            response.setDatabaseType(databaseType);
        }
        else {
            response.addError(error);
        }

        LaunchNewDatabaseInstanceValidator validator = new LaunchNewDatabaseInstanceValidator(
            response,
            templateId.orNull(),
            user.orNull()
        );

        validator.validate();

        if (response.isSuccessful()) {
            DatabaseIdGeneratorService databaseIdGeneratorService = new DatabaseIdGeneratorService();
            DatabaseId databaseId;
            databaseId = checkInstanceName(request, databaseIdGeneratorService);

            UsernameAndPasswordCredentialsGeneratorService credentialsGeneratorService = new UsernameAndPasswordCredentialsGeneratorService();
            credentialsGeneratorService.generate();

            DatabaseInstance databaseInstance = new DatabaseInstance(
                databaseId,
                templateId,
                databaseType,
                request.getInstanceName(),
                credentialsGeneratorService
            );
            databaseInstanceGateway.launchDatabase(databaseInstance);
            userGateway.save(user.get());

            response.setDatabaseId(databaseId);
        }
    }

    private DatabaseId checkInstanceName(LaunchNewDatabaseInstanceRequest request, DatabaseIdGeneratorService databaseIdGeneratorService) {
        DatabaseId databaseId;
        if (!request.getInstanceName().isPresent()) {
            databaseId = databaseIdGeneratorService.generate();
        }
        else {
            databaseId = databaseIdGeneratorService.generate(request.getInstanceName().get());
        }
        return databaseId;
    }

}
