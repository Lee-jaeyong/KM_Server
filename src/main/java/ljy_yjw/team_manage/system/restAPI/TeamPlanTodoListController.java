package ljy_yjw.team_manage.system.restAPI;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.dto.TodoListDTO;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.NotFoundException;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.plan.PlanAuthService;
import ljy_yjw.team_manage.system.service.auth.todoList.TodoListAuthService;
import ljy_yjw.team_manage.system.service.delete.todoList.TodoListOneDeleteService;
import ljy_yjw.team_manage.system.service.insert.todoList.TodoListOneInsertService;
import ljy_yjw.team_manage.system.service.read.todoList.TodoListReadService;
import ljy_yjw.team_manage.system.service.update.todoList.TodoListOneUpdateService;
import lombok.var;

@RestController
@RequestMapping("/api/teamManage/todoList")
public class TeamPlanTodoListController {

	@Autowired
	PlanAuthService planAuthService;

	@Autowired
	TodoListAuthService todoListAuthService;

	@Autowired
	TodoListReadService todoListReadService;

	@Autowired
	TodoListOneInsertService todoListOneInsertService;

	@Autowired
	TodoListOneUpdateService todoListOneUpdateService;

	@Autowired
	TodoListOneDeleteService todoListOneDeleteService;

	@GetMapping("{seq}")
	public ResponseEntity<?> getList(@PathVariable long seq, @Current_User Users user) throws PlanByUserNotAuthException {
		planAuthService.checkAuth(seq, user);
		List<TodoList> resultList = todoListReadService.getList(seq);
		CollectionModel<TodoList> result = new CollectionModel<TodoList>(resultList);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@PostMapping("{seq}")
	public ResponseEntity<?> saveTodoList(@PathVariable long seq, @RequestBody @Valid TodoListDTO todoListDTO,
		@Current_User Users user, Errors error) throws PlanByUserNotAuthException, InputValidException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		planAuthService.checkAuth(seq, user);
		PlanByUser planByUser = PlanByUser.builder().seq(seq).build();
		TodoList saveTodoList = todoListDTO.parseThis2TodoList(planByUser, user);
		saveTodoList = todoListOneInsertService.insertTodoList(saveTodoList);
		var result = new CustomEntityModel<>(saveTodoList, this, Long.toString(saveTodoList.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

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

	@DeleteMapping("{seq}")
	public ResponseEntity<?> deleteTodoList(@PathVariable long seq, @Current_User Users user) throws NotFoundException {
		todoListAuthService.authCheck(seq, user);
		TodoList deleteTodoList = todoListOneDeleteService.deleteTodoList(seq);
		var result = new CustomEntityModel<>(deleteTodoList, this, Long.toString(deleteTodoList.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}

}
