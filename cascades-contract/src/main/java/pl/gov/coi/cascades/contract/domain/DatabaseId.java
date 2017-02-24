package pl.gov.coi.cascades.contract.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class DatabaseId {

    @Getter
	private final String id;
}
