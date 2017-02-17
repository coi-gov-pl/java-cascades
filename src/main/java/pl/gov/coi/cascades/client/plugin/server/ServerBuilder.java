package pl.gov.coi.cascades.client.plugin.server;

public interface ServerBuilder {

	/**
	 *
	 * @param arquillianDefinition
	 */
	ServerBuilder useArquillianDefinition(String arquillianDefinition);

	Server build();

}