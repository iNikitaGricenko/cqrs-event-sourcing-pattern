package com.wolfhack.cqrspattern.factory;

import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserFactory {

	private ContactFactory contactFactory;
	private AddressFactory addressFactory;

	public UserEntity toEntity(User user) {
		if (user == null ) {
			return new UserEntity();
		}

		UserEntity entity = new UserEntity();

		Optional.ofNullable(user.getId()).ifPresent(entity::setId);
		Optional.ofNullable(user.getFirstName()).ifPresent(entity::setFirstName);
		Optional.ofNullable(user.getLastName()).ifPresent(entity::setLastName);
		Optional.ofNullable(user.getContacts()).map(contactFactory::toEntity).ifPresent(entity::setContacts);
		Optional.ofNullable(user.getAddresses()).map(addressFactory::toEntity).ifPresent(entity::setAddresses);

		return entity;
	}

	public User toUser(UserEntity entity) {
		Long id = entity.getId();
		String firstName = entity.getFirstName();
		String lastName = entity.getLastName();

		Set<User.Contact> contacts = Optional.ofNullable(entity.getContacts())
				.map(contactFactory::toContact).orElseGet(HashSet::new);

		Set<User.Address> addresses = Optional.ofNullable(entity.getAddresses())
				.map(addressFactory::toAddress).orElseGet(HashSet::new);

		return new User(id, firstName, lastName, contacts, addresses);
	}

	public UserEntity partialUpdate(User from, UserEntity to) {
		if (from == null) {
			return to;
		}

		Optional.ofNullable(from.getId()).ifPresent(to::setId);
		Optional.ofNullable(from.getFirstName()).ifPresent(to::setFirstName);
		Optional.ofNullable(from.getLastName()).ifPresent(to::setLastName);

		if (from.getContacts() != null ) {
			if (to.getContacts() == null) {
				to.setContacts(new HashSet<>());
			}
			to.setContacts(contactFactory.toEntity(from.getContacts()));
		}
		else {
			to.setContacts(null);
		}

		if (from.getAddresses() != null ) {
			if (to.getAddresses() == null) {
				to.setAddresses(new HashSet<>());
			}
			to.setAddresses(addressFactory.toEntity(from.getAddresses()));
		}
		else {
			to.setContacts(null);
		}

		return to;
	}

}
