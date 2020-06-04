package ljy_yjw.team_manage.system.service.insert.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.jpa.plan.PlanByUserAPI;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;

@Service
public class PlanByUserOneInsertService {
	
	@Autowired
	PlanByUserAPI planByUserAPI;
	
	public PlanByUser insertPlanByUser(PlanByUser planByUser) {
		return planByUserAPI.save(planByUser);
	}
}
