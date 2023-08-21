package com.wolfhack.cqrspattern.projector;

import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.model.domain.UserAddress;
import com.wolfhack.cqrspattern.model.domain.UserContact;
import com.wolfhack.cqrspattern.repository.UserReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserProjector {

	private final UserReadRepository readRepository = new UserReadRepository();

	public void project(User user) {
		UserContact userContact = Optional.ofNullable(readRepository.getUserContact(user.getId())).orElse(new UserContact());
		Map<String, Set<User.Contact>> contactByType = new HashMap<>();
		for (User.Contact contact : user.getContacts()) {
			Set<User.Contact> contacts = Optional.ofNullable(contactByType.get(contact.type())).orElse(new HashSet<>());
			contacts.add(contact);
			contactByType.put(contact.type(), contacts);
		}
		userContact.setContactByType(contactByType);
		readRepository.addUserContact(user.getId(), userContact);

		UserAddress userAddress = Optional.ofNullable(readRepository.getUserAddress(user.getId())).orElse(new UserAddress());
		Map<String, Set<User.Address>> addressByRegion = new HashMap<>();
		for (User.Address address : user.getAddresses()) {
			Set<User.Address> addresses = Optional.ofNullable(addressByRegion.get(address.state())).orElse(new HashSet<>());
			addresses.add(address);
			addressByRegion.put(address.state(), addresses);
		}
		userAddress.setAddressByRegion(addressByRegion);
		readRepository.addUserAddress(user.getId(), userAddress);
	}

}
