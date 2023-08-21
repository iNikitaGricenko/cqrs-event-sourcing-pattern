package com.wolfhack.cqrspattern.projection;

import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.model.domain.UserAddress;
import com.wolfhack.cqrspattern.model.domain.UserContact;
import com.wolfhack.cqrspattern.model.queries.AddressByRegionQuery;
import com.wolfhack.cqrspattern.model.queries.ContactByTypeQuery;
import com.wolfhack.cqrspattern.repository.UserReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserProjection {

	private final UserReadRepository readRepository;

	public Set<User.Contact> handle(ContactByTypeQuery query) {
		UserContact userContact = readRepository.getUserContact(query.getUserId());
		return userContact.getContactByType().get(query.getContactType());
	}

	public Set<User.Address> handle(AddressByRegionQuery query) {
		UserAddress userAddress = readRepository.getUserAddress(query.getUserId());
		return userAddress.getAddressByRegion().get(query.getState());
	}

}
