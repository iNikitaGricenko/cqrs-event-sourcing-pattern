package com.wolfhack.cqrspattern.repository;

import com.wolfhack.cqrspattern.factory.UserFactory;
import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserWriteRepository {

	private final Map<Long, UserEntity> fakeTable = new HashMap<Long, UserEntity>();
	private static long id = 1;

	private UserFactory userFactory;

	public long save(Long userId, User user) {
		UserEntity entity = userFactory.toEntity(user);
		Long entityId = Optional.ofNullable(entity.getId()).orElse(id);
		entity.setId(entityId);
		fakeTable.put(id, entity);
		id++;
		return entity.getId();
	}

	public Optional<User> findById(long id) {
		return Optional.ofNullable(fakeTable.get(id)).map(userFactory::toUser);
	}

	public void update(Long id, User user) {
		Optional.ofNullable(fakeTable.get(id))
				.map(entity -> userFactory.partialUpdate(user, entity))
				.ifPresent(entity -> fakeTable.replace(id, entity));
	}

}
