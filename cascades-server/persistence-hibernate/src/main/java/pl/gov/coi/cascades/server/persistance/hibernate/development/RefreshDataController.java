package pl.gov.coi.cascades.server.persistance.hibernate.development;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gov.coi.cascades.server.persistance.hibernate.development.data.JpaDevelopmentData;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.03.17.
 */
@Controller
@Profile("development")
@RequiredArgsConstructor
public class RefreshDataController {

    private final JpaDevelopmentData jpaDevelopmentData;

    @RequestMapping(path = "/development/refresh-data")
    public void refresh() {
        jpaDevelopmentData.down();

        jpaDevelopmentData.up();
    }

}
