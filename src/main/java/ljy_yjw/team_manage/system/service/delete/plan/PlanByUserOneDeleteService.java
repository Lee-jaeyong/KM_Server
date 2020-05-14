package ljy_yjw.team_manage.system.service.delete.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.mybatis.PlanByUserDAO;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;

@Service
public class PlanByUserOneDeleteService {

	@Autowired
	PlanByUserDAO planByUserDAO;

	public PlanByUser deletePlan(long seq) {
		planByUserDAO.delete(seq);
		return PlanByUser.builder().seq(seq).build();
	}
}
