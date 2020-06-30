package ljy_yjw.team_manage.system.restAPI.todoList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ljy_yjw.team_manage.system.custom.util.EntityFactory;
import ljy_yjw.team_manage.system.domain.dto.TodoListDTO;
import ljy_yjw.team_manage.system.domain.dto.valid.plan.PlanValidator;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.TodoListController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.insert.todoList.TodoListOneInsertService;

@TodoListController
public class TodoListInsertAPI {

	@Autowired
	PlanValidator planValidator;

	@Autowired
	TodoListOneInsertService todoListOneInsertService;

	@Autowired
	EntityFactory objectEntityFactory;

	@PostMapping("{seq}")
	public ResponseEntity<?> saveTodoList(@PathVariable long seq, @Valid @RequestBody TodoListDTO todo, Errors error,
		@Current_User Users user) throws PlanByUserNotAuthException, InputValidException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		planValidator.check(seq, user);
		PlanByUser planByUser = PlanByUser.builder().seq(seq).build();
		TodoList saveTodoList = todo.parseThis2TodoList(planByUser, user);
		saveTodoList = todoListOneInsertService.insertTodoList(saveTodoList);
		return ResponseEntity.ok(objectEntityFactory.get(saveTodoList, Long.toString(saveTodoList.getSeq())));
	}
}
