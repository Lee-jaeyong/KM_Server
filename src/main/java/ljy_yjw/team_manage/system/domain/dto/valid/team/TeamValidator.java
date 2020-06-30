package ljy_yjw.team_manage.system.domain.dto.valid.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ljy_yjw.team_manage.system.domain.dto.team.TeamDTO;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;

@Component
public class TeamValidator implements Validator {

	@Autowired
	TeamAuthService teamAuthService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.getClass().equals(TeamDTO.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TeamDTO team = (TeamDTO) target;
		if (team.getStartDate().isAfter(team.getEndDate())) {
			errors.rejectValue("startDate", "400", "시작일은 종료일보다 작아야합니다.");
		}
	}

	public void check(String code, Users user) throws TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code);
	}

}
