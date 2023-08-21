package com.wolfhack.cqrspattern.service;

import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.model.event.*;
import com.wolfhack.cqrspattern.repository.EventInMemoryStore;

import java.util.HashSet;
import java.util.List;

public class UserUtility {

	public static User recreateUserState(EventInMemoryStore store, Long userId) {
		User user = null;

		List<Event> events = store.getEvents(userId);
		for (Event event : events) {
			user = recreateUserState(event, user);
		}

		return user;
	}

	private static User recreateUserState(Event event, User user) {
		if (event instanceof UserCreatedEvent e) {
			user = new User(e.getUserId(), e.getFirstName(), e.getLastName(), new HashSet<>(), new HashSet<>());
		}

		if (event instanceof UserAddressAddedEvent) {
			recreate((UserAddressAddedEvent) event, user);
		}

		if (event instanceof UserAddressRemovedEvent) {
			recreate((UserAddressRemovedEvent) event, user);
		}

		if (event instanceof UserContactAddedEvent) {
			recreate((UserContactAddedEvent) event, user);
		}

		if (event instanceof UserContactRemovedEvent) {
			recreate((UserContactRemovedEvent) event, user);
		}

		return user;
	}

	private static void recreate(UserAddressAddedEvent event, User user) {
		User.Address address = new User.Address(event.getCity(), event.getState(), event.getPostCode());
		if (user != null) {
			user.getAddresses().add(address);
		}
	}

	private static void recreate(UserAddressRemovedEvent event, User user) {
		User.Address address = new User.Address(event.getCity(), event.getState(), event.getPostCode());
		if (user != null) {
			user.getAddresses().remove(address);
		}
	}

	private static void recreate(UserContactAddedEvent event, User user) {
		User.Contact contact = new User.Contact(event.getContactType(), event.getContactDetails());
		if (user != null) {
			user.getContacts().add(contact);
		}
	}

	private static void recreate(UserContactRemovedEvent event, User user) {
		User.Contact contact = new User.Contact(event.getContactType(), event.getContactDetails());
		if (user != null) {
			user.getContacts().remove(contact);
		}
	}

}
