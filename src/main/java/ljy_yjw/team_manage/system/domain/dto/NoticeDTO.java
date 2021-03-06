package ljy_yjw.team_manage.system.domain.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import ljy_yjw.team_manage.system.domain.entity.Notice;
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
@Alias("NoticeDTO")
public class NoticeDTO {
	long seq;
	@NotNull(message = "제목을 입력해주세요")
	String title;
	@NotNull(message = "내용을 입력해주세요")
	String content;

	public Notice parseThis2Notice(Team team, Users user) {
		return Notice.builder()
			.user(user)
			.team(team)
			.title(this.title)
			.content(this.content)
			.state(BooleanState.YES)
			.date(new Date())
			.build();
	}
}
