package ljy_yjw.team_manage.system.domain.dto;

import javax.validation.constraints.NotNull;

import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoListDTO {

	@NotNull(message = "타이틀을 입력해주세요.")
	String title;

	BooleanState ing;

	public TodoList parseThis2TodoList(PlanByUser plan, Users user) {
		return TodoList.builder().user(user).ing(BooleanState.NO).state(BooleanState.YES).planByUser(plan).title(this.title).build();
	}
}
