package ljy_yjw.team_manage.system.domain.dto.plan;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date start;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date end;

	String userId;

	BooleanState finished = BooleanState.NO;
	BooleanState allFinished = BooleanState.NO;
	BooleanState teamPlan = BooleanState.NO;
	
	Integer todoCount;

	String title = "";
	String todo = "";
}
