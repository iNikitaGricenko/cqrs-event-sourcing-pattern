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
public class EventUserService implements UserInput {

	private final UserInMemoryRepository userInMemoryRepository;
	private final EventInMemoryStore eventInMemoryStore;

	@Override
	public void createUser(String firstName, String lastName) {
		User user = new User(null, firstName, lastName, new HashSet<>(), new HashSet<>());
		long saved = userInMemoryRepository.save(user);
		eventInMemoryStore.addEvent(saved, new UserCreatedEvent(saved, firstName, lastName));
	}

	@Override
	public void updateUser(Long userId, Set<User.Contact> contacts, Set<User.Address> addresses) {
		User user = UserUtility.recreateUserState(eventInMemoryStore, userId);

		user.getContacts().stream()
				.filter(c -> !contacts.contains(c))
				.forEach(c -> eventInMemoryStore.addEvent(userId, new UserContactRemovedEvent(c.type(), c.detail())));

		contacts.stream()
				.filter(c -> !user.getContacts().contains(c))
				.forEach(c -> eventInMemoryStore.addEvent(userId, new UserContactAddedEvent(c.type(), c.detail())));

		user.getAddresses().stream()
				.filter(a -> !addresses.contains(a))
				.forEach(a -> eventInMemoryStore.addEvent(userId, new UserAddressRemovedEvent(a.city(), a.state(), a.postcode())));

		addresses.stream()
				.filter(a -> !user.getAddresses().contains(a))
				.forEach(a -> eventInMemoryStore.addEvent(userId, new UserAddressAddedEvent(a.city(), a.state(), a.postcode())));
	}

	public Set<User.Contact> getContactByType(Long userId, String contactType) {
		User user = UserUtility.recreateUserState(eventInMemoryStore, userId);
		return user.getContacts().stream()
				.filter(contact -> contact.type().equals(contactType))
				.collect(Collectors.toSet());
	}

	public Set<User.Address> getAddressByRegion(Long userId, String state) {
		User user = UserUtility.recreateUserState(eventInMemoryStore, userId);
		return user.getAddresses().stream()
				.filter(address -> address.state().equals(state))
				.collect(Collectors.toSet());
	}

}
