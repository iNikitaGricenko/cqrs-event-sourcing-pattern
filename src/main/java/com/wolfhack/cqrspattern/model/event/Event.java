package com.wolfhack.cqrspattern.model.event;

import lombok.Data;

import java.util.Date;
import java.util.UUID;


@Data
public abstract class Event {

	public final UUID id = UUID.randomUUID();
	public final Date created = new Date();

}
