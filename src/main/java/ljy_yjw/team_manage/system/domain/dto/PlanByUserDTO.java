package ljy_yjw.team_manage.system.domain.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import ljy_yjw.team_manage.system.custom.util.CustomDate;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.exception.exceptions.CheckInputValidException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("PlanByUserDTO")
public class PlanByUserDTO {

	long seq;

	@NotNull(message = "태그를 입력해주세요")
	String tag;

	@NotNull(message = "일정 시작일을 입력해주세요")
	LocalDate start;

	@NotNull(message = "일정 종료일을 입력해주세요")
	LocalDate end;

	BooleanState teamPlan;

	public PlanByUser parseThis2PlanByUser(PlanByUserDTO planByUser, Team team, Users user) {
		return PlanByUser.builder().tag(this.tag).start(CustomDate.LocalDate2Date(this.start))
			.end(CustomDate.LocalDate2Date(this.end)).state(BooleanState.YES).teamPlan(BooleanState.NO)
			.team(team).user(user).build();
	}

	public void isAfter() throws CheckInputValidException {
		if (this.start.isAfter(this.end)) {
			throw new CheckInputValidException("시작일은 종료일보다 작아야합니다.");
		}
	}
}
