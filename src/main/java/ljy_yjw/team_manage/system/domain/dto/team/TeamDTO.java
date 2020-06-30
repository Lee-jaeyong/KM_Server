package ljy_yjw.team_manage.system.domain.dto.team;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;

import ljy_yjw.team_manage.system.custom.util.CustomDate;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class TeamDTO {
	protected long seq;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected String code;
	
	@NotNull(message = "팀 최종 목표를 입력해주세요.")
	@Size(min = 1, max = 100, message = "팀 목표는 1자 이상 100자 미만으로 입력해주세요.")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected String description;

	@NotNull(message = "팀 시작일을 입력해주세요.")
	@PastOrPresent(message = "팀 시작일은 현재일로부터 과거일 기준으로 수정할 수 있습니다.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected LocalDate startDate;

	
	@NotNull(message = "팀 마감일을 입력해주세요.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future(message = "팀 종료일을 현재일자로부터 과거일자로 수정할 수 없습니다.")
	protected LocalDate endDate;
	
	public Team parseThis2Team(Users user) {
		return Team.builder().code(this.code).startDate(CustomDate.LocalDate2Date(this.startDate))
			.endDate(CustomDate.LocalDate2Date(this.endDate)).description(this.description).teamLeader(user)
			.flag(BooleanState.YES).build();
	}
}
