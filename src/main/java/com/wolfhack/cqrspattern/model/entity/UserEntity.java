package com.wolfhack.cqrspattern.model.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class UserEntity {

	@Id
	private Long id;

	private String firstName;
	private String lastName;

	@ElementCollection
	private Set<ContactEntity> contacts;

	@ElementCollection
	private Set<AddressEntity> addresses;

	@Override
	public final boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}

		Class<?> oEffectiveClass = object instanceof HibernateProxy ?
				((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
				((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();

		if (thisEffectiveClass != oEffectiveClass) {
			return false;
		}
		UserEntity that = (UserEntity) object;
		return getId() != null && Objects.equals(getId(), that.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ?
				((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}
