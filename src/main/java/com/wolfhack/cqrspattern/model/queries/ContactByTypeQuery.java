package com.wolfhack.cqrspattern.model.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactByTypeQuery {

	private Long userId;
	private String contactType;

}
