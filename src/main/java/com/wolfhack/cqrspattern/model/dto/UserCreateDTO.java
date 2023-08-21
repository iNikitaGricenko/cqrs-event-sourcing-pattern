package com.wolfhack.cqrspattern.model.dto;

import com.wolfhack.cqrspattern.model.entity.UserEntity;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link UserEntity}
 */
@Value
public class UserCreateDTO implements Serializable {
	String firstName;
	String lastName;
	Set<ContactDto> contacts;
	Set<AddressDto> addresses;
}