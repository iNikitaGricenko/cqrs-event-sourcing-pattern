package com.wolfhack.cqrspattern.factory;

import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.model.entity.AddressEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddressFactory {

	public AddressEntity toEntity(User.Address address) {
		AddressEntity entity = new AddressEntity();

		Optional.ofNullable(address.city()).ifPresent(entity::setCity);
		Optional.ofNullable(address.state()).ifPresent(entity::setState);
		Optional.ofNullable(address.postcode()).ifPresent(entity::setPostcode);

		return entity;
	}

	public Set<AddressEntity> toEntity(Collection<User.Address> addresses) {
		return addresses.stream().map(this::toEntity).collect(Collectors.toSet());
	}

	public User.Address toAddress(AddressEntity address) {
		String city = Optional.ofNullable(address.getCity()).orElse("");
		String state = Optional.ofNullable(address.getState()).orElse("");
		String postcode = Optional.ofNullable(address.getPostcode()).orElse("");

		return new User.Address(city, state, postcode);
	}

	public Set<User.Address> toAddress(Collection<AddressEntity> addresses) {
		return addresses.stream().map(this::toAddress).collect(Collectors.toSet());
	}

	public AddressEntity partialUpdate(User.Address from, AddressEntity to) {
		if (from == null) {
			return to;
		}

		Optional.ofNullable(from.city()).ifPresent(to::setCity);
		Optional.ofNullable(from.state()).ifPresent(to::setState);
		Optional.ofNullable(from.postcode()).ifPresent(to::setPostcode);

		return to;
	}

}
