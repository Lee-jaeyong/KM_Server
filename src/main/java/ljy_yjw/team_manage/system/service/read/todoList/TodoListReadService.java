package ljy_yjw.team_manage.system.service.read.todoList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.jpa.TodoListAPI;
import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

@Service
public class TodoListReadService {

	@Autowired
	TodoListAPI todoListAPI;

	public List<TodoList> getList(long seq) {
		return todoListAPI.findByPlanByUser_SeqAndState(seq, BooleanState.YES);
	}
}
