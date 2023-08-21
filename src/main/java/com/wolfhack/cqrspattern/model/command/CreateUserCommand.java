package com.wolfhack.cqrspattern.model.command;

import java.io.Serializable;

public record CreateUserCommand(Long userId, String firstName, String lastName) implements Serializable {
}
