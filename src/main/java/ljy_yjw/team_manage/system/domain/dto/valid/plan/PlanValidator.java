package ljy_yjw.team_manage.system.domain.dto.valid.plan;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ljy_yjw.team_manage.system.custom.util.CustomDate;
import ljy_yjw.team_manage.system.domain.dto.plan.PlanByUserDTO;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.plan.PlanByUserNotAuthException;
import ljy_yjw.team_manage.system.service.auth.plan.PlanAuthService;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;

@Component
public class PlanValidator implements Validator {

	@Autowired
	PlanAuthService planAuthService;

	@Autowired
	TeamAuthService teamAuthService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.getClass().equals(PlanByUserDTO.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PlanByUserDTO plan = (PlanByUserDTO) target;
		if (plan.getStart().isAfter(plan.getEnd())) {
			errors.rejectValue("start", "400", "일정 시작일은 종료일보다 작아야합니다.");
		}
	}

	public void checkDate(PlanByUserDTO plan, String message) throws CanNotPerformException {
		if (plan.getStart().isAfter(plan.getEnd()))
			throw new CanNotPerformException(message);
	}

	public void validate(Team team, PlanByUserDTO planByUserDTO, Errors errors) {
		Date planStart = CustomDate.LocalDate2Date(planByUserDTO.getStart());
		Date planEnd = CustomDate.LocalDate2Date(planByUserDTO.getEnd());

		if (planStart.after(planEnd)) {
			errors.rejectValue("start", "400", "일정 시작일은 종료일보다 작거나 같아야합니다.");
		} else if (planStart.before(team.getStartDate()) || planStart.after(team.getEndDate())) {
			errors.rejectValue("start", "400", "일정은 팀이 진행되는 기간에만 등록할 수 있습니다.");
		} else if (planEnd.after(team.getEndDate())) {
			errors.rejectValue("end", "400", "일정은 팀이 진행되는 기간에만 등록할 수 있습니다.");
		}

		validate(planByUserDTO, errors);
	}

	public void check(long seq, Users user) throws PlanByUserNotAuthException {
		planAuthService.checkAuth(seq, user);
	}
}
