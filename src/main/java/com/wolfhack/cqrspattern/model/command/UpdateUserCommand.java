package com.wolfhack.cqrspattern.model.command;

import com.wolfhack.cqrspattern.model.domain.User;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

public record UpdateUserCommand(long userId, Set<User.Address> addresses, Set<User.Contact> contacts) implements Serializable {
}
