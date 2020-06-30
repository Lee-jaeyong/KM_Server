package ljy_yjw.team_manage.system.dbConn.queryDsl.plan;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.queryDsl.CommonQueryDsl;
import ljy_yjw.team_manage.system.domain.dto.plan.SearchDTO;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.QPlanByUser;
import ljy_yjw.team_manage.system.domain.entity.QTodoList;
import ljy_yjw.team_manage.system.domain.entity.QUsers;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

@Repository
public class PlanQueryDsl extends CommonQueryDsl {

	@PersistenceContext
	EntityManager em;

	QPlanByUser planByUser = QPlanByUser.planByUser;
	QTodoList todoList = QTodoList.todoList;
	QUsers user = QUsers.users;

	@Memo("특정월에 해야하는 일정 가져오기")
	public List<PlanByUser> findAllByCodeAndMonth(String code, SearchDTO searchDTO, Pageable pageable) {
		JPAQuery<PlanByUser> query = new JPAQuery<>(em);
		query = getQuery(code, searchDTO).orderBy(planByUser.seq.desc());
		return paging(query, pageable).fetch();
	}

	@Memo("특정 월에 해야하는 일정 개수 가져오기")
	public long countAllByCodeAndMonth(String code, SearchDTO searchDTO) {
		JPAQuery<PlanByUser> query = new JPAQuery<>(em);
		query = getQuery(code, searchDTO);
		return query.fetchCount();
	}

	private JPAQuery<PlanByUser> getQuery(String code, SearchDTO searchDTO) {
		JPAQuery<PlanByUser> query = new JPAQuery<>(em);
		query = query.from(planByUser);
		BooleanExpression where = addCheckTitle(searchDTO.getTitle());
		if (code != null) {
			where = where.and(addCheckCode(code));
		}
		if (searchDTO.getStart() != null && searchDTO.getEnd() != null) {
			where = where.and(addCheckDateMonth(searchDTO.getStart(), searchDTO.getEnd()));
		}

		if (searchDTO.getUserId() != null) {
			where = where.and(addCheckUser(searchDTO.getUserId()));
		}

		if (searchDTO.getFinished() == BooleanState.FINISHED) {
			where = where.and(finishedPlan());
		}

		if (searchDTO.getTodoCount() != null) {
			where = where.and(checkTodoCount(searchDTO.getTodoCount()));
		}

		if (searchDTO.getTeamPlan() == BooleanState.YES) {
			where = where.and(checkTeamPlan());
		}

		if (!searchDTO.getTodo().equals("")) {
			where = where.and(checkTodo(searchDTO.getTodo())).and(todoList.state.ne(BooleanState.NO));
			query = query.innerJoin(planByUser.todoList, todoList).fetchJoin();
		}
		
		query = query.innerJoin(planByUser.user, user).fetchJoin()
			.where(where.and(planByUser.state.ne(BooleanState.NO)));
		return query;
	}

	private BooleanExpression checkTodo(String todo) {
		return todoList.title.contains(todo);
	}

	private BooleanExpression checkTeamPlan() {
		return planByUser.teamPlan.eq(BooleanState.YES);
	}

	private BooleanExpression checkTodoCount(int count) {
		return planByUser.todoList.size().eq(count);
	}

	private BooleanExpression finishedPlan() {
		return planByUser.end.before(new Date());
	}

	private BooleanExpression addCheckUser(String id) {
		return user.id.eq(id);
	}

	private BooleanExpression addCheckCode(String code) {
		return planByUser.team.code.eq(code);
	}

	private BooleanExpression addCheckTitle(String title) {
		return planByUser.tag.contains(title);
	}

	private BooleanExpression addCheckDateMonth(Date start, Date end) {
		return planByUser.start.after(start).or(planByUser.start.eq(start)).and(planByUser.start.before(end))
			.or(planByUser.start.eq(end));
	}
}
