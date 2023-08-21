package com.wolfhack.cqrspattern.repository;

import com.wolfhack.cqrspattern.model.domain.UserAddress;
import com.wolfhack.cqrspattern.model.domain.UserContact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserReadRepository {

    private final Map<Long, UserAddress> userAddress = new HashMap<>();

    private final Map<Long, UserContact> userContact = new HashMap<>();

    public void addUserAddress(Long id, UserAddress user) {
        userAddress.put(id, user);
    }

    public UserAddress getUserAddress(Long id) {
        return userAddress.get(id);
    }

    public void addUserContact(Long id, UserContact user) {
        userContact.put(id, user);
    }

    public UserContact getUserContact(Long id) {
        return userContact.get(id);
    }

}
