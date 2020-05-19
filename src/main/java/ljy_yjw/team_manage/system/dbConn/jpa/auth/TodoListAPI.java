package ljy_yjw.team_manage.system.dbConn.jpa.auth;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

public interface TodoListAPI extends JpaRepository<TodoList, Long> {

	@EntityGraph(attributePaths = { "user", "planByUser" }, type = EntityGraphType.LOAD)
	List<TodoList> findByPlanByUser_SeqAndState(long seq, BooleanState state);

	TodoList findBySeqAndUser_Id(long seq, String id);
}
