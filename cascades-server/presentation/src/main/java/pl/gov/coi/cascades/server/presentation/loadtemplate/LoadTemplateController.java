package pl.gov.coi.cascades.server.presentation.loadtemplate;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pl.gov.coi.cascades.contract.service.WithViolations;
import pl.gov.coi.cascades.server.domain.loadtemplate.Request;
import pl.gov.coi.cascades.server.domain.loadtemplate.Upload;
import pl.gov.coi.cascades.server.domain.loadtemplate.UseCase;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 12.05.17.
 */
@Controller
public class LoadTemplateController {

    private final UseCase useCase;

    @Inject
    public LoadTemplateController(UseCase useCase) {
        this.useCase = useCase;
    }

    @RequestMapping(
        value = "",
        method = RequestMethod.POST,
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ResponseBody
    public ResponseEntity<WithViolations<RemoteTemplateSpec>> checkTemplate(
        @RequestParam("file") MultipartFile request) throws IOException {

        Upload upload = Upload.builder()
            .inputStream(request.getInputStream())
            .size(request.getSize())
            .contentType(request.getContentType())
            .build();

        Request.RequestBuilder requestBuilder = Request.builder()
            .name(request.getOriginalFilename())
            .isEmpty(request.isEmpty())
            .upload(upload);

        Request templateRequest = requestBuilder.build();
        Presenter presenter = new Presenter();

        useCase.execute(
            templateRequest,
            presenter
        );
        return presenter.createModel();
    }

    @GetMapping("/")
    public String loadTemplate() {
        return "upload";
    }

}
