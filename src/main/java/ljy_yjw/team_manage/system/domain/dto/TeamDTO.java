package ljy_yjw.team_manage.system.domain.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;

import ljy_yjw.team_manage.system.custom.util.CustomDate;
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
@Alias("TeamDTO")
public class TeamDTO {
	long seq;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	String code;

	@NotNull(message = "팀명을 입력해주세요")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String name;
	@NotNull(message = "팀 시작일을 입력해주세요")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate startDate;
	@NotNull(message = "팀 마감일을 입력해주세요")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate endDate;
	@NotNull(message = "팀 최종 목표를 입력해주세요")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String description;

	public Team parseThis2Team(Users user) {
		return Team.builder().name(this.name).code(this.code).startDate(CustomDate.LocalDate2Date(this.startDate))
			.endDate(CustomDate.LocalDate2Date(this.endDate)).description(this.description).teamLeader(user)
			.flag(BooleanState.YES).build();
	}

	public void isAfter() throws CheckInputValidException {
		if (this.startDate.isAfter(this.endDate)) {
			throw new CheckInputValidException("시작일은 종료일보다 작아야합니다.");
		}
	}
}
