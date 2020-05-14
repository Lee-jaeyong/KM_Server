package ljy_yjw.team_manage.system.domain.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;

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

	@NotNull(message = "일정 내용을 입력해주세요")
	String content;

	@NotNull(message = "일정 시작일을 입력해주세요")
	LocalDate start;

	@NotNull(message = "일정 종료일을 입력해주세요")
	LocalDate end;

	BooleanState teamPlan;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	byte progress;

	public PlanByUser parseThis2PlanByUser(PlanByUserDTO planByUser, Team team, Users user) {
		return PlanByUser.builder().tag(this.tag).content(this.content).start(CustomDate.LocalDate2Date(this.start))
			.end(CustomDate.LocalDate2Date(this.end)).state(BooleanState.YES).teamPlan(BooleanState.NO).progress(this.progress)
			.team(team).user(user).build();
	}

	public void isAfter() throws CheckInputValidException {
		if (this.start.isAfter(this.end)) {
			throw new CheckInputValidException("시작일은 종료일보다 작아야합니다.");
		}
	}

	public void checkProgress() throws CheckInputValidException {
		if (this.progress > 100 || this.progress < 0) {
			throw new CheckInputValidException("진척도는 0 이상 100 이하로 입력해주세요");
		}
	}
}
