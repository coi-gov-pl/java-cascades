package pl.gov.coi.cascades.contract.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class TemplateId {

    @Getter
	private final String id;
}
