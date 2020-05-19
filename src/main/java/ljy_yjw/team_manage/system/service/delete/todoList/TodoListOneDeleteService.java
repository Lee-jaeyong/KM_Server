package ljy_yjw.team_manage.system.service.delete.todoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.mybatis.TodoListDAO;
import ljy_yjw.team_manage.system.domain.entity.TodoList;

@Service
public class TodoListOneDeleteService {

	@Autowired
	TodoListDAO todoListDAO;
	
	public TodoList deleteTodoList(long seq){
		todoListDAO.delete(seq);
		return TodoList.builder().seq(seq).build();
	}
}
