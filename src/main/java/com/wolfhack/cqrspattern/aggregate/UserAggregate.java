package com.wolfhack.cqrspattern.aggregate;

import com.wolfhack.cqrspattern.model.command.CreateUserCommand;
import com.wolfhack.cqrspattern.model.command.UpdateUserCommand;
import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.repository.UserWriteRepository;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;

@RequiredArgsConstructor
public class UserAggregate {

	private final UserWriteRepository userWriteRepository;

	public User handleCreateUserCommand(CreateUserCommand command) {
		User user = new User(command.userId(), command.firstName(), command.lastName(), new HashSet<>(), new HashSet<>());
		userWriteRepository.save(user.getId(), user);
		return user;
	}

	public User handleUpdateUserCommand(UpdateUserCommand command) {
		User user = userWriteRepository.findById(command.userId()).orElseThrow();
		user.setAddresses(command.addresses());
		user.setContacts(command.contacts());
		userWriteRepository.update(user.getId(), user);
		return user;
	}

}
