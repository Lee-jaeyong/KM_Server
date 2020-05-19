package ljy_yjw.team_manage.system.service.insert.todoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.jpa.auth.TodoListAPI;
import ljy_yjw.team_manage.system.domain.entity.TodoList;

@Service
public class TodoListOneInsertService {

	@Autowired
	TodoListAPI todoListAPI;

	public TodoList insertTodoList(TodoList todoList) {
		return todoListAPI.save(todoList);
	}
}
