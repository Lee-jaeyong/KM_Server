package ljy_yjw.team_manage.system.service.delete.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.mybatis.PlanByUserDAO;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;

@Service
public class PlanByUserOneDeleteService {

	@Autowired
	PlanByUserDAO planByUserDAO;

	public PlanByUser deletePlan(PlanByUser planByUser) {
		planByUserDAO.delete(planByUser.getSeq());
		return PlanByUser.builder().seq(planByUser.getSeq()).build();
	}
}
