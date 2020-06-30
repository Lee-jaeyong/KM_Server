package ljy_yjw.team_manage.system.domain.dto.plan;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ljy_yjw.team_manage.system.custom.util.CustomDate;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
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
public class PlanByUserDTO {

	long seq;

	@NotNull(message = "태그를 입력해주세요")
	@Pattern(regexp = "^[가-힣\\s]*$", message = "태그는 한글 조합으로 입력해주세요.")
	@Size(min = 1, max = 20, message = "태그는 2자 이상 20자 미만으로 입력해주세요.")
	String tag;

	@NotNull(message = "일정 시작일을 입력해주세요")
	LocalDate start;

	@NotNull(message = "일정 종료일을 입력해주세요")
	LocalDate end;

	BooleanState teamPlan;

	public PlanByUser parseThis2PlanByUser(PlanByUserDTO planByUser, Team team, Users user) {
		return PlanByUser.builder().tag(this.tag).start(CustomDate.LocalDate2Date(this.start))
			.end(CustomDate.LocalDate2Date(this.end)).state(BooleanState.YES).teamPlan(BooleanState.NO).team(team).user(user)
			.build();
	}
}
