package com.wolfhack.cqrspattern.aggregate;

import com.wolfhack.cqrspattern.model.command.CreateUserCommand;
import com.wolfhack.cqrspattern.model.command.UpdateUserCommand;
import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.model.event.*;
import com.wolfhack.cqrspattern.repository.EventInMemoryStore;
import com.wolfhack.cqrspattern.repository.UserWriteRepository;
import com.wolfhack.cqrspattern.service.UserUtility;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
public class UserEventAggregate {

	private final EventInMemoryStore eventInMemoryStore;

	public List<Event> handleCreateUserCommand(CreateUserCommand command) {
		UserCreatedEvent event = new UserCreatedEvent(command.userId(), command.firstName(), command.lastName());
		eventInMemoryStore.addEvent(command.userId(), event);
		return List.of(event);
	}

	public List<Event> handleUpdateUserCommand(UpdateUserCommand command) {
		User user = UserUtility.recreateUserState(eventInMemoryStore, command.userId());
		List<Event> events = new ArrayList<>();

		removeContacts(command, user, events);
		addContacts(command, user, events);

		removeAddresses(command, user, events);
		addAddresses(command, user, events);

		return events;
	}

	private void removeContacts(UpdateUserCommand command, User user, List<Event> events) {
		List<User.Contact> contactsToRemove = user.getContacts().stream()
				.filter(contact -> !command.contacts().contains(contact))
				.toList();

		for (User.Contact contact : contactsToRemove) {
			UserContactRemovedEvent contactRemovedEvent = new UserContactRemovedEvent(contact.type(), contact.detail());
			events.add(contactRemovedEvent);
			eventInMemoryStore.addEvent(command.userId(), contactRemovedEvent);
		}
	}

	private void addContacts(UpdateUserCommand command, User user, List<Event> events) {
		List<User.Contact> contactsToAdd = command.contacts().stream()
				.filter(contact -> !user.getContacts().contains(contact))
				.toList();

		for (User.Contact contact : contactsToAdd) {
			UserContactAddedEvent contactAddedEvent = new UserContactAddedEvent(contact.type(), contact.detail());
			events.add(contactAddedEvent);
			eventInMemoryStore.addEvent(command.userId(), contactAddedEvent);
		}
	}

	private void removeAddresses(UpdateUserCommand command, User user, List<Event> events) {
		List<User.Address> addressesToRemove = user.getAddresses().stream()
				.filter(address -> !command.addresses().contains(address))
				.toList();

		for (User.Address address : addressesToRemove) {
			UserAddressRemovedEvent contactRemovedEvent = new UserAddressRemovedEvent(address.city(), address.state(), address.postcode());
			events.add(contactRemovedEvent);
			eventInMemoryStore.addEvent(command.userId(), contactRemovedEvent);
		}
	}

	private void addAddresses(UpdateUserCommand command, User user, List<Event> events) {
		List<User.Address> addressesToAdd = command.addresses().stream()
				.filter(address -> !user.getAddresses().contains(address))
				.toList();

		for (User.Address address : addressesToAdd) {
			UserAddressAddedEvent contactAddedEvent = new UserAddressAddedEvent(address.city(), address.state(), address.postcode());
			events.add(contactAddedEvent);
			eventInMemoryStore.addEvent(command.userId(), contactAddedEvent);
		}
	}

}
