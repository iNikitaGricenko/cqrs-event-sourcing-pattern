package com.wolfhack.cqrspattern.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AddressEntity {

	private String city;
	private String state;
	private String postcode;

}
