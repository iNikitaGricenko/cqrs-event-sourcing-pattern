package com.wolfhack.cqrspattern.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.wolfhack.cqrspattern.model.entity.AddressEntity}
 */
@Value
public class AddressDto implements Serializable {
	String city;
	String state;
	String postcode;
}