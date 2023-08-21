package com.wolfhack.cqrspattern.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserAddressAddedEvent extends Event {

	private String city;
	private String state;
	private String postCode;

}
