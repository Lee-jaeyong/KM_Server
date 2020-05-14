package ljy_yjw.team_manage.system.domain.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
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
@Alias("FreeBoardDTO")
public class FreeBoardDTO {
	long seq;
	@NotNull(message = "제목을 입력해주세요")
	String title;
	@NotNull(message = "내용을 입력해주세요")
	String content;
	
	public FreeBoard parseThis2FreeBoard(Users user,Team team) {
		return FreeBoard.builder()
			.date(new Date())
			.content(this.content)
			.title(this.title)
			.state(BooleanState.YES)
			.user(user)
			.team(team)
			.build();
	}
}
