package ljy_yjw.team_manage.system.service.update.todoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.mybatis.TodoListDAO;
import ljy_yjw.team_manage.system.domain.entity.TodoList;

@Service
public class TodoListOneUpdateService {

	@Autowired
	TodoListDAO todoListDAO;

	public TodoList updateTodoList(long seq, TodoList todoList) {
		todoList.setSeq(seq);
		todoListDAO.update(todoList);
		return todoList;
	}

	public TodoList updateIng(long seq) {
		todoListDAO.updateIng(seq);
		return TodoList.builder().seq(seq).build();
	}
	
	public TodoList updateIngFaild(long seq) {
		todoListDAO.updateIngFaild(seq);
		return TodoList.builder().seq(seq).build();
	}
}
