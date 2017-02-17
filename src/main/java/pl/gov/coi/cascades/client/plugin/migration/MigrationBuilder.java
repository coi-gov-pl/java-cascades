package pl.gov.coi.cascades.client.plugin.migration;

public interface MigrationBuilder {

	/**
	 *
	 * @param prefix
	 */
	MigrationBuilder setPrefix(String prefix);

	/**
	 *
	 * @param scheme
	 */
	MigrationBuilder setScheme(String scheme);

	Migration build();

}