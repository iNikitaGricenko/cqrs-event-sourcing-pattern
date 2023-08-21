package com.wolfhack.cqrspattern.factory;

import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.model.entity.ContactEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContactFactory {

	public ContactEntity toEntity(User.Contact contact) {
		ContactEntity entity = new ContactEntity();

		Optional.ofNullable(contact.detail()).ifPresent(entity::setDetail);
		Optional.ofNullable(contact.type()).ifPresent(entity::setType);

		return entity;
	}

	public Set<ContactEntity> toEntity(Collection<User.Contact> contacts) {
		return contacts.stream().map(this::toEntity).collect(Collectors.toSet());
	}

	public User.Contact toContact(ContactEntity entity) {
		String type = Optional.ofNullable(entity.getType()).orElse("");
		String detail = Optional.ofNullable(entity.getDetail()).orElse("");

		return new User.Contact(type, detail);
	}

	public Set<User.Contact> toContact(Collection<ContactEntity> entities) {
		return entities.stream().map(this::toContact).collect(Collectors.toSet());
	}

	public ContactEntity partialUpdate(User.Contact from, ContactEntity to) {
		if (from == null) {
			return to;
		}

		Optional.ofNullable(from.type()).ifPresent(to::setType);
		Optional.ofNullable(from.detail()).ifPresent(to::setDetail);

		return to;
	}

}
