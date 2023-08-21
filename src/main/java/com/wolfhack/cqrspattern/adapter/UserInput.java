package com.wolfhack.cqrspattern.adapter;

import com.wolfhack.cqrspattern.model.domain.User;

import java.util.Set;

public interface UserInput {
	void createUser(String firstName, String lastName);

	void updateUser(Long userId, Set<User.Contact> contacts, Set<User.Address> addresses);
}
