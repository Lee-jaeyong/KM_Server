package ljy_yjw.team_manage.system.restAPI.todoList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;
import ljy_yjw.team_manage.system.restAPI.TodoListController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.plan.PlanAuthService;
import ljy_yjw.team_manage.system.service.read.todoList.TodoListReadService;

@TodoListController
public class TodoListOneGetAPI {

	@Autowired
	PlanAuthService planAuthService;

	@Autowired
	TodoListReadService todoListReadService;

	@GetMapping("{seq}")
	public ResponseEntity<?> getList(@PathVariable long seq, @Current_User Users user) throws PlanByUserNotAuthException {
		planAuthService.checkAuth(seq, user);
		List<TodoList> resultList = todoListReadService.getList(seq);
		CollectionModel<TodoList> result = new CollectionModel<TodoList>(resultList);
		result.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}
}
