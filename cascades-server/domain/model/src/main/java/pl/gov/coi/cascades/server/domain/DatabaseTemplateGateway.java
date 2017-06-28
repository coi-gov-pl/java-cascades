package pl.gov.coi.cascades.server.domain;

import java.nio.file.Path;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 28.06.17
 */
public interface DatabaseTemplateGateway {

    void loadTemplate(Path path);

}
