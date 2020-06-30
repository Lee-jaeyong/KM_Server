package ljy_yjw.team_manage.system.domain.dto.team;

import com.fasterxml.jackson.annotation.JsonInclude;

import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamUpdateDTO extends TeamDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;

	public Team parseThis2Team(Users user) {
		Team team = super.parseThis2Team(user);
		return team;
	}
}
