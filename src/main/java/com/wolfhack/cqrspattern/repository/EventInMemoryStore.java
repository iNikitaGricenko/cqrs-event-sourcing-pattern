package com.wolfhack.cqrspattern.repository;

import com.wolfhack.cqrspattern.model.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class EventInMemoryStore {
	private final Map<Long, List<Event>> store = new HashMap<>();

	public void addEvent(Long id, Event event) {
		List<Event> events = store.get(id);
		if (events == null) {
			events = new ArrayList<Event>();
			events.add(event);
			store.put(id, events);
		} else {
			events.add(event);
		}
	}

	public List<Event> getEvents(Long id) {
		return store.get(id);
	}
}
