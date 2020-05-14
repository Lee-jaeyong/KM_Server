package ljy_yjw.team_manage.system.domain.dto;

import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinTeamDTO {
	long seq;
	String date;
	BooleanState state;
}
