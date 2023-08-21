package com.wolfhack.cqrspattern.model.domain;

import com.wolfhack.cqrspattern.model.entity.AddressEntity;
import com.wolfhack.cqrspattern.model.entity.ContactEntity;
import com.wolfhack.cqrspattern.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
public final class User implements Serializable {
	private Long id;
	private String firstName;
	private String lastName;
	private Set<Contact> contacts;
	private Set<Address> addresses;

	/**
	 * DTO for {@link ContactEntity}
	 */
	public record Contact(String type, String detail) implements Serializable {
	}

	/**
	 * DTO for {@link AddressEntity}
	 */
	public record Address(String city, String state, String postcode) implements Serializable {
	}
}