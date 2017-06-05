package pl.gov.coi.cascades.server.presentation.loadtemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pl.gov.coi.cascades.contract.service.WithViolations;
import pl.gov.coi.cascades.server.domain.loadtemplate.Request;
import pl.gov.coi.cascades.server.domain.loadtemplate.UseCase;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        Request.RequestBuilder requestBuilder = Request.builder()
            .zipFile(request.getInputStream())
            .name(request.getOriginalFilename())
            .size(request.getSize())
            .isEmpty(request.isEmpty())
            .contentType(request.getContentType());

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


    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "F://temp//";

    //Single file upload
//    @PostMapping("")
    // If not @RestController, uncomment this
//    @RequestMapping(
//        value = "/api/upload",
//        method = RequestMethod.POST
//    )
    @ResponseBody
    public ResponseEntity uploadFile(
        @RequestParam("file") MultipartFile uploadfile) {


        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {
            saveUploadedFiles(Arrays.asList(uploadfile));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
            uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }

    // Multiple file upload
//    @RequestMapping(
//        value = "/api/upload/multi",
//        method = RequestMethod.POST
//    )
    @PostMapping("/api/upload/multi")
    @ResponseBody
    public ResponseEntity uploadFileMulti(
        @RequestParam("extraField") String extraField,
        @RequestParam("files") MultipartFile[] uploadfiles) {


        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
            .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfiles));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - "
            + uploadedFileName, HttpStatus.OK);

    }

    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
            }
        }

    }

}
