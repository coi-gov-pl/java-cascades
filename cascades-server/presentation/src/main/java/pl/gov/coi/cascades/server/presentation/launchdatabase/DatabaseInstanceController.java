package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseInstanceRequest;
import pl.gov.coi.cascades.server.domain.launchdatabase.DatabaseInstanceUseCase;
import pl.gov.coi.cascades.server.presentation.UserSession;

import javax.inject.Inject;
import java.util.concurrent.Callable;

@Controller
public class DatabaseInstanceController {

    private final DatabaseInstanceUseCase databaseInstanceUseCase;
    private final UserSession userSession;
    private final OptionalMapper optionalMapper;

    @Inject
    public DatabaseInstanceController(UserSession userSession,
                                      DatabaseInstanceUseCase databaseInstanceUseCase,
                                      OptionalMapper optionalMapper) {
        this.userSession = userSession;
        this.databaseInstanceUseCase = databaseInstanceUseCase;
        this.optionalMapper = optionalMapper;
    }

    /**
     * Method gives future of specification of remote database for given request.
     *
     * @param request Given database creation request.
     * @return a future of remote database specification
     */
    @RequestMapping(
        value = "/databases",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Callable<RemoteDatabaseSpec> launchDatabasePost(@RequestBody RemoteDatabaseRequestDTO request) {
        User user = userSession.getSignedInUser();

        DatabaseInstanceRequest.DatabaseInstanceRequestBuilder requestBuilder = DatabaseInstanceRequest.builder()
            .typeClassName(request.getTypeClassName())
            .user(user);

        optionalMapper.toJava8(request.getTemplateId())
            .ifPresent(templateId -> requestBuilder.templateId(templateId.getId()));
        optionalMapper.toJava8(request.getInstanceName())
            .ifPresent(requestBuilder::instanceName);

        DatabaseInstanceRequest databaseInstanceRequest = requestBuilder.build();
        DatabaseInstancePresenter databaseInstancePresenter = new DatabaseInstancePresenter();

        return () -> {

            databaseInstanceUseCase.execute(
                databaseInstanceRequest,
                databaseInstancePresenter
            );

            return databaseInstancePresenter.createModel();
        };
    }

}
