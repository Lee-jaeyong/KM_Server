package ljy_yjw.team_manage.system.service.update.plan;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.mybatis.PlanByUserDAO;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;

@Service
public class PlanByUserOneUpdateService {

	@Autowired
	PlanByUserDAO planByUserDAO;

	@Memo("일정을 수정하는 메소드")
	@Transactional
	public PlanByUser updatePlanByUser(PlanByUser planByUser) {
		planByUserDAO.update(planByUser);
		return planByUser;
	}
}
