package ljy.book.admin.professor.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public Page<PlanByUser> getAll(String code) {
		return planByUserAPI.findByStateAndTeam_Code(BooleanState.YSE, code, PageRequest.of(0, 50));
	}

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

	@Transactional
	public boolean update(long seq, PlanByUserDTO planByUserDTO) {
		planByUserDTO.setSeq(seq);
		planByUserDAO.update(planByUserDTO);
		return true;
	}
}
