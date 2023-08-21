package com.wolfhack.cqrspattern.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserCreatedEvent extends Event {

	private Long userId;
	private String firstName;
	private String lastName;

}
