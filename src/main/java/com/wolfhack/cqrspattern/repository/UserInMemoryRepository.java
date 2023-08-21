package com.wolfhack.cqrspattern.repository;

import com.wolfhack.cqrspattern.factory.UserFactory;
import com.wolfhack.cqrspattern.model.domain.User;
import com.wolfhack.cqrspattern.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserInMemoryRepository {

	private final Map<Long, UserEntity> fakeTable = new HashMap<Long, UserEntity>();
	private static long id = 1;

	private UserFactory userFactory;

	public long save(User user) {
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

	public Page<User> findAll(Pageable pageable) {
		int totalSize = fakeTable.size();
		List<User> content = fakeTable.values().stream().limit(pageable.getPageSize()).map(userFactory::toUser).toList();
		return new PageImpl<>(content, pageable, totalSize);
	}

	public void update(Long id, User user) {
		Optional.ofNullable(fakeTable.get(id))
				.map(entity -> userFactory.partialUpdate(user, entity))
				.ifPresent(entity -> fakeTable.replace(id, entity));
	}

	public void delete(Long id) {
		fakeTable.remove(id);
	}

}
