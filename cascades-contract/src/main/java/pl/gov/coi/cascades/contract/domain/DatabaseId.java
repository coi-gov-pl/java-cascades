package pl.gov.coi.cascades.contract.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@RequiredArgsConstructor
public class DatabaseId implements Serializable {

    private static final long serialVersionUID = 42L;

    @Getter
	private final String id;
}
