package pl.gov.coi.cascades.server.presentation.launchdatabase;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.gov.coi.cascades.contract.service.RemoteDatabaseSpec;
import pl.gov.coi.cascades.server.domain.User;
import pl.gov.coi.cascades.server.domain.launchdatabase.Request;
import pl.gov.coi.cascades.server.domain.launchdatabase.UseCase;
import pl.gov.coi.cascades.server.presentation.UserSession;

import javax.inject.Inject;
import java.util.concurrent.Callable;

@org.springframework.stereotype.Controller
public class Controller {

    private final UseCase useCase;
    private final UserSession userSession;
    private final OptionalMapper optionalMapper;

    @Inject
    public Controller(UserSession userSession,
                      UseCase useCase,
                      OptionalMapper optionalMapper) {
        this.userSession = userSession;
        this.useCase = useCase;
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

        Request.RequestBuilder requestBuilder = Request.builder()
            .typeClassName(request.getTypeClassName())
            .user(user);

        optionalMapper.toJava8(request.getTemplateId())
            .ifPresent(templateId -> requestBuilder.templateId(templateId.getId()));
        optionalMapper.toJava8(request.getInstanceName())
            .ifPresent(requestBuilder::instanceName);

        Request databaseInstanceRequest = requestBuilder.build();
        Presenter databaseInstancePresenter = new Presenter();

        return () -> {

            useCase.execute(
                databaseInstanceRequest,
                databaseInstancePresenter
            );

            return databaseInstancePresenter.createModel();
        };
    }

}
