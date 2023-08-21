package com.wolfhack.cqrspattern.service;

import com.wolfhack.cqrspattern.adapter.UserInput;
import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.model.event.*;
import com.wolfhack.cqrspattern.repository.EventInMemoryStore;
import com.wolfhack.cqrspattern.repository.UserInMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CQRSUserService implements UserInput {

	private final UserInMemoryRepository userInMemoryRepository;

	@Override
	public void createUser(String firstName, String lastName) {
		User user = new User(null, firstName, lastName, new HashSet<>(), new HashSet<>());
		userInMemoryRepository.save(user);
	}

	@Override
	public void updateUser(Long userId, Set<User.Contact> contacts, Set<User.Address> addresses) {
		User user = userInMemoryRepository.findById(userId).orElseThrow();
		user = new User(user.getId(), user.getFirstName(), user.getLastName(), contacts, addresses);
		userInMemoryRepository.update(userId, user);
	}

	public Set<User.Contact> getContactByType(Long userId, String contactType) {
		User user = userInMemoryRepository.findById(userId).orElseThrow();
		Set<User.Contact> contacts = user.getContacts();
		return contacts.stream().filter(contact -> contact.type().equals(contactType)).collect(Collectors.toSet());
	}

	public Set<User.Address> getAddressByRegion(Long userId, String state) {
		User user = userInMemoryRepository.findById(userId).orElseThrow();
		Set<User.Address> addresses = user.getAddresses();
		return addresses.stream().filter(address -> address.state().equals(state)).collect(Collectors.toSet());
	}

}
