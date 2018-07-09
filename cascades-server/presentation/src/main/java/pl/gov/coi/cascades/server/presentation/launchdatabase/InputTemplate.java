package pl.gov.coi.cascades.server.presentation.launchdatabase;

import pl.gov.coi.cascades.contract.domain.Template;
import pl.gov.coi.cascades.contract.domain.TemplateIdStatus;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 14.04.17.
 */
class InputTemplate extends Template {

    private static final long serialVersionUID = 42L;

    private static final TemplateIdStatus DEFAULT_STATUS = null;
    private static final boolean IS_DEFAULT = false;
    private static final String DEFAULT_SERVER_ID = null;
    private static final String VERSION = null;
    private static final String NAME = null;
    private static final Long ID = null;

    InputTemplate(String generatedId) {
        super(ID, generatedId, NAME, DEFAULT_STATUS, IS_DEFAULT, DEFAULT_SERVER_ID, VERSION);
    }

}
