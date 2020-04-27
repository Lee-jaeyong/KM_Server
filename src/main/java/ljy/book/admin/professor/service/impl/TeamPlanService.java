package ljy.book.admin.professor.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.customRepository.mybaits.PlanByUserDAO;
import ljy.book.admin.entity.PlanByUser;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.jpaAPI.PlanByUserAPI;
import ljy.book.admin.professor.requestDTO.DateRequestDTO;
import ljy.book.admin.professor.requestDTO.PlanByUserDTO;

@Service
public class TeamPlanService {

	@Autowired
	PlanByUserAPI planByUserAPI;

	@Autowired
	PlanByUserDAO planByUserDAO;

	@Transactional
	@Memo("개인별 일정 개수 가져오기")
	public List<HashMap<String, Object>> getPlanCountGroupByUser(String code) {
		return planByUserDAO.chartDataByPlan(code);
	}

	@Transactional
	@Memo("자신의 모든 일정 가져오기")
	public Page<PlanByUser> getMyPlanAllUnFinished(String search, Users user, Pageable pageable) {
		return planByUserAPI.findByStateAndUser_IdAndTagContainsIgnoreCaseOrContentContainsIgnoreCaseOrderBySeq(BooleanState.YES,
			user.getId(), search, search, pageable);
	}

	@Transactional
	@Memo("자신의 끝난 모든 일정 가져오기")
	public Page<PlanByUser> getMyPlanAllFinished(String search, Users user, Pageable pageable) {
		return planByUserAPI.findByStateAndUser_IdAndTagContainsIgnoreCaseOrContentContainsIgnoreCaseOrderBySeq(BooleanState.NO,
			user.getId(), search, search, pageable);
	}

	@Transactional
	@Memo("단건 조회")
	public PlanByUser getOne(long seq) {
		return planByUserAPI.findBySeqAndState(seq, BooleanState.YES);
	}

	@Transactional
	@Memo("리스트 조회")
	public Page<PlanByUser> getAll(String code) {
		return planByUserAPI.findByStateAndTeam_Code(BooleanState.YES, code, PageRequest.of(0, 100));
	}

	@Transactional
	@Memo("특정 일자에 해야하는 일정 가져오기")
	public Page<PlanByUser> getSearch(String code, String date, Pageable pageable) {
		return planByUserAPI.findByStateAndTeam_CodeAndEndLessThanEqualAndStartGreaterThanEqual(BooleanState.YES, code, date,
			date, pageable);
	}

	@Transactional
	@Memo("특정 달의 리스트 조회")
	public Page<PlanByUser> getSearchAll(String code, String firstDate, String lastDate) {
		return planByUserAPI.findByStateAndTeam_CodeAndStartGreaterThanEqual(BooleanState.YES, code, firstDate,
			PageRequest.of(0, 100));
	}

	@Transactional
	@Memo("시퀀스를 통해 권한이 있는가를 확인 후 있다면 리턴")
	public PlanByUser checkAuthPlanSuccessThenGet(long seq, Users user) {
		return planByUserAPI.findBySeqAndUser_Id(seq, user.getId());
	}

	@Transactional
	@Memo("삭제")
	public boolean delete(long seq) {
		planByUserDAO.delete(seq);
		return true;
	}

	@Transactional
	@Memo("추가")
	public PlanByUser save(long seq, PlanByUserDTO planByUserDTO, Users user) {
		Team team = new Team();
		team.setSeq(seq);
		PlanByUser savePlan = PlanByUser.builder().state(BooleanState.YES).progress(planByUserDTO.getProgress())
			.content(planByUserDTO.getContent()).end(planByUserDTO.getEnd()).start(planByUserDTO.getStart())
			.tag(planByUserDTO.getTag())
			.teamPlan(planByUserDTO.getTeamPlan() == null || planByUserDTO.getTeamPlan() == BooleanState.NO ? BooleanState.NO
				: BooleanState.YES)
			.build();
		Users me = new Users();
		me.setSeq(user.getSeq());
		team.addPlan(savePlan);
		me.addPlan(savePlan);
		return planByUserAPI.save(savePlan);
	}

	@Transactional
	@Memo("수정")
	public boolean update(long seq, PlanByUserDTO planByUserDTO) {
		planByUserDTO.setSeq(seq);
		planByUserDAO.update(planByUserDTO);
		return true;
	}

	@Transactional
	@Memo("일정에 대한 진척도를 변경")
	public boolean updateProgress(long seq, int progress) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("progress", progress);
		planByUserDAO.updateProgress(map);
		return true;
	}

}
