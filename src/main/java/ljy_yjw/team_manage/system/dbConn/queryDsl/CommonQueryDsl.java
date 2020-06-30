package ljy_yjw.team_manage.system.dbConn.queryDsl;

import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQuery;

public abstract class CommonQueryDsl {
	protected <T> JPAQuery<T> paging(JPAQuery<T> query, Pageable pageable) {
		return query.offset(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize());
	}
}
