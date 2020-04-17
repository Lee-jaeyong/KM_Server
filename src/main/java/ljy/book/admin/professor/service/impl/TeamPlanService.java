package ljy.book.admin.professor.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy.book.admin.customRepository.mybaits.PlanByUserDAO;
import ljy.book.admin.entity.PlanByUser;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.jpaAPI.PlanByUserAPI;
import ljy.book.admin.professor.requestDTO.PlanByUserDTO;

@Service
public class TeamPlanService {

	@Autowired
	PlanByUserAPI planByUserAPI;

	@Autowired
	PlanByUserDAO planByUserDAO;

	@Transactional
	public PlanByUser checkAuthPlanSuccessThenGet(long seq, Users user) {
		return planByUserAPI.findBySeqAndUser_Id(seq, user.getId());
	}

	@Transactional
	public boolean delete(long seq) {
		planByUserDAO.delete(seq);
		return true;
	}

	@Transactional
	public PlanByUser save(long seq, PlanByUserDTO planByUserDTO, Users user) {
		Team team = new Team();
		team.setSeq(seq);
		PlanByUser savePlan = PlanByUser.builder().state(BooleanState.YSE).progress((byte) 0).content(planByUserDTO.getContent())
			.end(planByUserDTO.getEnd()).start(planByUserDTO.getStart()).tag(planByUserDTO.getTag())
			.teamPlan(planByUserDTO.getTeamPlan() == null || planByUserDTO.getTeamPlan() == BooleanState.NO ? BooleanState.NO
				: BooleanState.YSE)
			.build();
		Users me = new Users();
		me.setSeq(user.getSeq());
		team.addPlan(savePlan);
		me.addPlan(savePlan);
		return planByUserAPI.save(savePlan);
	}
}
