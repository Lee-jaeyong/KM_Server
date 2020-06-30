package ljy_yjw.team_manage.system.domain.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

	@Size(max = 20, min = 1, message = "단건 일정은 1자 이상, 20자 이하로 입력해주세요.")
	@NotNull(message = "단건 일정을 입력해주세요.")
	String title;

	BooleanState ing;

	public TodoList parseThis2TodoList(PlanByUser plan, Users user) {
		return TodoList.builder().user(user).ing(BooleanState.NO).state(BooleanState.YES).planByUser(plan).title(this.title)
			.build();
	}
}
