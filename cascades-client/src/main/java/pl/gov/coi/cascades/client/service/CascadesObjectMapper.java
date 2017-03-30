package pl.gov.coi.cascades.client.service;

import com.mashape.unirest.http.ObjectMapper;

/**
 * @author <a href="mailto:krzysztof.suszynski@coi.gov.pl">Krzysztof Suszynski</a>
 * @since 30.03.17
 */
public class CascadesObjectMapper implements ObjectMapper {
    @Override
    public <T> T readValue(String value, Class<T> valueType) {
        // TODO: write some implementation code
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public String writeValue(Object value) {
        // TODO: write some implementation code
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
