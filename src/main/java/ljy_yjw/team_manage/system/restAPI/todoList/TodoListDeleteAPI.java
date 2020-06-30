package ljy_yjw.team_manage.system.restAPI.todoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.NotFoundException;
import ljy_yjw.team_manage.system.restAPI.TodoListController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.todoList.TodoListAuthService;
import ljy_yjw.team_manage.system.service.delete.todoList.TodoListOneDeleteService;
import lombok.var;

@TodoListController
public class TodoListDeleteAPI {

	@Autowired
	TodoListAuthService todoListAuthService;

	@Autowired
	TodoListOneDeleteService todoListOneDeleteService;

	@DeleteMapping("{seq}")
	public ResponseEntity<?> deleteTodoList(@PathVariable long seq, @Current_User Users user) throws NotFoundException {
		todoListAuthService.authCheck(seq, user);
		TodoList deleteTodoList = todoListOneDeleteService.deleteTodoList(seq);
		var result = new CustomEntityModel<>(deleteTodoList, this, Long.toString(deleteTodoList.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

}
