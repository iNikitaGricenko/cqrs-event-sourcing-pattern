package com.wolfhack.cqrspattern.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ContactEntity {

	private String type;
	private String detail;

}
