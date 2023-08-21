package com.wolfhack.cqrspattern.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.wolfhack.cqrspattern.model.entity.ContactEntity}
 */
@Value
public class ContactDto implements Serializable {
	String type;
	String detail;
}