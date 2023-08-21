package com.wolfhack.cqrspattern.model.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class UserAddress {

	private Map<String, Set<User.Address>> addressByRegion = new HashMap<>();

}
