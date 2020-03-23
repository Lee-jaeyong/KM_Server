package ljy.book.admin.common.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class CommonRepositoryimpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements CommonRepository<T, ID>{

	EntityManager em;

	public CommonRepositoryimpl(JpaEntityInformation<T,?> jpaEntityInformation, EntityManager em) {
		super(jpaEntityInformation, em);
		this.em = em;
	}

	@Override
	public boolean contains(T entity) {
		return em.contains(entity);
	}
}
