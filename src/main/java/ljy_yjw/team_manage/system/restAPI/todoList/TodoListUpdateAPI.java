package ljy_yjw.team_manage.system.restAPI.todoList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.dto.TodoListDTO;
import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.NotFoundException;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.TodoListController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.todoList.TodoListAuthService;
import ljy_yjw.team_manage.system.service.update.todoList.TodoListOneUpdateService;
import lombok.var;

@TodoListController
public class TodoListUpdateAPI {

	@Autowired
	TodoListAuthService todoListAuthService;

	@Autowired
	TodoListOneUpdateService todoListOneUpdateService;

	@PutMapping("{seq}")
	public ResponseEntity<?> updateTodoList(@PathVariable long seq, @RequestBody @Valid TodoListDTO todoListDTO,
		@Current_User Users user, Errors error) throws InputValidException, PlanByUserNotAuthException, NotFoundException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		todoListAuthService.authCheck(seq, user);
		TodoList updateTodoList = todoListOneUpdateService.updateTodoList(seq, todoListDTO.parseThis2TodoList(null, null));
		var result = new CustomEntityModel<>(updateTodoList, this, Long.toString(updateTodoList.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

	@PutMapping("{seq}/faild")
	public ResponseEntity<?> faildTodoList(@PathVariable long seq, @Current_User Users user) throws NotFoundException {
		todoListAuthService.authCheck(seq, user);
		TodoList faildTodoList = todoListOneUpdateService.updateIngFaild(seq);
		var result = new CustomEntityModel<>(faildTodoList, this, Long.toString(faildTodoList.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

	@PutMapping("{seq}/success")
	public ResponseEntity<?> successTodoList(@PathVariable long seq, @Current_User Users user)
		throws InputValidException, PlanByUserNotAuthException, NotFoundException {
		todoListAuthService.authCheck(seq, user);
		TodoList successTodoList = todoListOneUpdateService.updateIng(seq);
		var result = new CustomEntityModel<>(successTodoList, this, Long.toString(successTodoList.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

}
