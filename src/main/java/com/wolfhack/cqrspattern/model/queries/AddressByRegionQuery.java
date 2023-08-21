package com.wolfhack.cqrspattern.model.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressByRegionQuery {

	private Long userId;
	private String state;
}
