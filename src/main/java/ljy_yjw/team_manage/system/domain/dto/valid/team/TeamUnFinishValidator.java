package ljy_yjw.team_manage.system.domain.dto.valid.team;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ljy_yjw.team_manage.system.custom.util.CustomDate;
import ljy_yjw.team_manage.system.domain.entity.Team;

@Component
public class TeamUnFinishValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.getClass().equals(Team.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Team team = (Team) target;
		String endDate = CustomDate.dateToString(team.getEndDate());
		String now = CustomDate.getNowDate();
		if (endDate.compareTo(now) > 0) {
			errors.rejectValue("endDate", "400", "현재 진행중인 팀은 복구할 수 없습니다.");
		}
	}
}
