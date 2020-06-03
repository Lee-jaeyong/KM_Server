package ljy_yjw.team_manage.system.service.insert.plan;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.CheckInputValidException;
import ljy_yjw.team_manage.system.service.insert.todoList.TodoListOneInsertService;
import ljy_yjw.team_manage.system.service.read.plan.PlanExcelReadService;

@Service
public class PlanByUserExcelInsertService {

	@Autowired
	PlanByUserOneInsertService planByUserOneInsertService;

	@Autowired
	TodoListOneInsertService todoListOneInsertService;

	@Autowired
	PlanExcelReadService planExcelReadService;

	@Transactional
	public boolean excelFileUpload(MultipartFile file, List<Users> users, Team team)
		throws CanNotPerformException, IOException, CheckInputValidException {
		List<PlanByUser> plans = planExcelReadService.excelDataRead(file, users, team);
		plans.forEach(c -> {
			c.setTeamPlan(BooleanState.NO);
			c.setTeam(team);
			c.setState(BooleanState.YES);
			PlanByUser resultPlan = planByUserOneInsertService.insertPlanByUser(c);
			c.getTodoList().forEach(t -> {
				t.getPlanByUser().setSeq(resultPlan.getSeq());
				t.setUser(c.getUser());
				t.setState(BooleanState.YES);
				t.setIng(BooleanState.NO);
				todoListOneInsertService.insertTodoList(t);
			});
		});
		return true;
	}
}
