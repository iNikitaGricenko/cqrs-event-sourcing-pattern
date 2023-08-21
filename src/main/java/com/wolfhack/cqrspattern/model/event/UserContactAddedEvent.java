package com.wolfhack.cqrspattern.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserContactAddedEvent extends Event {

	private String contactType;
	private String contactDetails;

}
