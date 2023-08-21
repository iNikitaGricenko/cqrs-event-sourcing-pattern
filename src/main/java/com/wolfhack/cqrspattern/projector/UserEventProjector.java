package com.wolfhack.cqrspattern.projector;

import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.model.domain.UserAddress;
import com.wolfhack.cqrspattern.model.domain.UserContact;
import com.wolfhack.cqrspattern.model.event.*;
import com.wolfhack.cqrspattern.repository.UserReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserEventProjector {

	private final UserReadRepository readRepository = new UserReadRepository();

	public void project(Long userId, List<Event> events) {
		for (Event event : events) {
			if (event instanceof UserAddressAddedEvent) {
				apply(userId, (UserAddressAddedEvent) event);
			}

			if (event instanceof UserAddressRemovedEvent) {
				apply(userId, (UserAddressRemovedEvent) event);
			}

			if (event instanceof UserContactAddedEvent) {
				apply(userId, (UserContactAddedEvent) event);
			}

			if (event instanceof UserContactRemovedEvent) {
				apply(userId, (UserContactRemovedEvent) event);
			}
		}
	}

	public void apply(Long userId, UserAddressAddedEvent event) {
		User.Address address = new User.Address(event.getCity(), event.getState(), event.getPostCode());
		UserAddress userAddress = Optional.ofNullable(readRepository.getUserAddress(userId)).orElse(new UserAddress());
		Set<User.Address> addresses = Optional.ofNullable(userAddress.getAddressByRegion().get(address.state())).orElse(new HashSet<>());
		addresses.add(address);
		userAddress.getAddressByRegion().put(address.state(), addresses);
		readRepository.addUserAddress(userId, userAddress);
	}

	public void apply(Long userId, UserAddressRemovedEvent event) {
		User.Address address = new User.Address(event.getCity(), event.getState(), event.getPostCode());
		UserAddress userAddress = readRepository.getUserAddress(userId);
		if (userAddress != null) {
			Set<User.Address> addresses = userAddress.getAddressByRegion().get(address.state());
			if (addresses != null) {
				addresses.remove(address);
			}
			readRepository.addUserAddress(userId, userAddress);
		}
	}

	public void apply(Long userId, UserContactAddedEvent event) {
		User.Contact contact = new User.Contact(event.getContactType(), event.getContactDetails());
		UserContact userContact = Optional.ofNullable(readRepository.getUserContact(userId)).orElse(new UserContact());
		Set<User.Contact> contacts = Optional.ofNullable(userContact.getContactByType().get(contact.type())).orElse(new HashSet<>());
		contacts.add(contact);
		userContact.getContactByType().put(contact.type(), contacts);
		readRepository.addUserContact(userId, userContact);
	}

	public void apply(Long userId, UserContactRemovedEvent event) {
		User.Contact contact = new User.Contact(event.getContactType(), event.getContactDetails());
		UserContact userContact = readRepository.getUserContact(userId);
		if (userContact != null) {
			Set<User.Contact> contacts = userContact.getContactByType().get(contact.type());
			if (contacts != null) {
				contacts.remove(contact);
			}
			readRepository.addUserContact(userId, userContact);
		}
	}

}
