package pl.gov.coi.cascades.contract.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@RequiredArgsConstructor
@ToString
public class TemplateId implements Serializable {

    private static final long serialVersionUID = 42L;

    @Getter
	private final String id;
}
