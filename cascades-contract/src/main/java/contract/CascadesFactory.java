package contract;

import contract.service.CascadesService;

import javax.security.auth.login.Configuration;

public interface CascadesFactory {

	/**
	 * 
	 * @param configuration
	 * @param service
	 */
	Cascades create(Configuration configuration, CascadesService service);

}