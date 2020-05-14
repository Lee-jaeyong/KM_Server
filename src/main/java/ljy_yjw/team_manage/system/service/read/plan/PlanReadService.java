package ljy_yjw.team_manage.system.service.read.plan;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.PlanByUserAPI;
import ljy_yjw.team_manage.system.dbConn.mybatis.PlanByUserDAO;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

@Service
public class PlanReadService {

	@Autowired
	PlanByUserAPI planByUserAPI;

	@Autowired
	PlanByUserDAO planByUserDAO;

	@Memo("해당 번호의 일정을 가져오는 메소드")
	public PlanByUser getPlanByUser(long seq) throws NotFoundException {
		PlanByUser resultPlan = planByUserAPI.findBySeq(seq);
		if (resultPlan == null) {
			throw new NotFoundException("해당 일정이 존재하지 않습니다.");
		}
		return resultPlan;
	}

	@Memo("각각의 일정 개수를 가져오는 메소드")
	public List<HashMap<String, Object>> getPlanCountGroupByUser(String code) {
		return planByUserDAO.chartDataByPlan(code);
	}

	@Memo("자신의 모든 일정 가져오기")
	public List<PlanByUser> getPlanByMy(String id, String search, String code, Pageable pageable, GetType type) {
		if (type == GetType.FINISHED) {
			return planByUserAPI.findByStateAndUser_IdAndTeam_CodeAndEndLessThan(BooleanState.YES, id, code, new Date(),
				pageable);
		}
		if (code == null)
			return planByUserAPI.findByStateAndUser_IdAndTagContainsIgnoreCaseOrContentContainsIgnoreCaseOrderBySeq(
				BooleanState.YES, id, search, search, pageable);
		return planByUserAPI.findByStateAndUser_IdAndTeam_CodeAndTagContainsIgnoreCaseOrContentContainsIgnoreCaseOrderBySeq(
			BooleanState.YES, id, code, search, search, pageable);
	}

	@Memo("자신의 모든 일정 개수 가져오기")
	public long countPlanByMy(String id, String search, String code, GetType type) {
		if (type == GetType.FINISHED) {
			return planByUserAPI.countByStateAndUser_IdAndTeam_CodeAndEndLessThan(BooleanState.YES, id, code, new Date());
		}
		if (code == null)
			return planByUserAPI.countByStateAndUser_IdAndTagContainsIgnoreCaseOrContentContainsIgnoreCaseOrderBySeq(
				BooleanState.YES, id, search, search);
		return planByUserAPI.countByStateAndUser_IdAndTeam_CodeAndTagContainsIgnoreCaseOrContentContainsIgnoreCaseOrderBySeq(
			BooleanState.YES, id, code, search, search);
	}

	@Memo("해당 코드의 팀의 일정을 가져오는 메소드")
	public List<PlanByUser> getPlanList(String code, Pageable pageable, Date date, GetType type) {
		if (type == GetType.SEARCH) {
			return planByUserAPI.findByStateAndTeam_CodeAndEndLessThan(BooleanState.YES, code,
				new Date(date.getYear(), date.getMonth() + 1, 1), pageable);
		}

		if (type == GetType.FINISHED) {
			return planByUserAPI.findByStateAndTeam_CodeAndEndLessThan(BooleanState.YES, code, new Date(), pageable);
		}

		if (date == null)
			return planByUserAPI.findByStateAndTeam_Code(BooleanState.YES, code, pageable);
		else {
			return planByUserAPI.findByStateAndTeam_CodeAndEndGreaterThanEqualAndStartLessThanEqual(BooleanState.YES, code, date,
				date, pageable);
		}
	}

	@Memo("해당 코드의 팀의 일정 개수를 모두 가져오는 메소드")
	public long getPlanCount(String code, Date date, GetType type) {
		if (type == GetType.SEARCH) {
			return planByUserAPI.countByStateAndTeam_CodeAndEndLessThan(BooleanState.YES, code,
				new Date(date.getYear(), date.getMonth() + 1, 1));
		}
		if (type == GetType.FINISHED) {
			return planByUserAPI.countByStateAndTeam_CodeAndEndLessThan(BooleanState.YES, code, new Date());
		}
		if (date == null)
			return planByUserAPI.countByStateAndTeam_Code(BooleanState.YES, code);
		else {
			return planByUserAPI.countByStateAndTeam_CodeAndEndGreaterThanEqualAndStartLessThanEqual(BooleanState.YES, code, date,
				date);
		}
	}

	public enum GetType {
		SEARCH, FINISHED, NON
	}
}
