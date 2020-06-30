package ljy_yjw.team_manage.system.domain.dto.team;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class TeamInsertDTO extends TeamDTO {

	@NotNull(message = "팀명을 입력해주세요")
	@Pattern(regexp = "^[가-힣\\s]*$", message = "팀명은 한글 조합으로 입력해주세요.")
	@Size(min = 1, max = 50, message = "팀 명은 1자 이상 50자 미만으로 입력해주세요.")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;

	public Team parseThis2Team(Users user) {
		Team team = super.parseThis2Team(user);
		team.setName(name);
		return team;
	}
}
