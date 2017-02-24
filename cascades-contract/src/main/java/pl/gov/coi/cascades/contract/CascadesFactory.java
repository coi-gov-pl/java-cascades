package pl.gov.coi.cascades.contract;

import pl.gov.coi.cascades.contract.service.CascadesLaunchService;

import javax.security.auth.login.Configuration;

public interface CascadesFactory {

	/**
	 * Method gives cascades for given configuration and service of cascades.
	 * @param configuration Given configuration.
	 * @param service Given service.
	 */
	Cascades create(Configuration configuration, CascadesLaunchService service);

}
