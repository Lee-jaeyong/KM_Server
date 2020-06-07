package ljy_yjw.team_manage.system.service.read.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.plan.PlanByUserAPI;
import ljy_yjw.team_manage.system.dbConn.mybatis.PlanByUserDAO;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.service.CommonFileUpload;

@Service
public class PlanReadService {

	@Autowired
	PlanByUserAPI planByUserAPI;

	@Autowired
	PlanByUserDAO planByUserDAO;

	@Autowired
	CommonFileUpload commonFileUpload;

	@Memo("해당 번호의 일정을 가져오는 메소드")
	public PlanByUser getPlanByUser(long seq) throws NotFoundException {
		PlanByUser resultPlan = planByUserAPI.findBySeq(seq);
		if (resultPlan == null) {
			throw new NotFoundException("해당 일정이 존재하지 않습니다.");
		}
		return resultPlan;
	}

	@Memo("해당 팀의 월별 일정률을 가져오는 메소드(마감일자)")
	public List<HashMap<String, Object>> getPlanCountByEndDate(String code) {
		return planByUserDAO.chartDataByPlanEnd(code);
	}

	@Memo("해당 팀의 월별 일정률을 가져오는 메소드(진행일자)")
	public List<HashMap<String, Object>> getPlanCountByStartDate(String code) {
		return planByUserDAO.chartDataByPlanStart(code);
	}

	@Memo("각각의 일정 개수를 가져오는 메소드")
	public List<HashMap<String, Object>> getPlanCountGroupByUser(String code) {
		List<HashMap<String, Object>> result = new ArrayList<>();
		List<HashMap<String, Object>> todoChart = planByUserDAO.chartDataByTodo(code);
		List<HashMap<String, Object>> planChart = planByUserDAO.chartDataByPlan(code);
		List<HashMap<String, Object>> successTodoChart = planByUserDAO.chartDataBySuccessTodo(code);
		planChart.forEach(c -> {
			HashMap<String, Object> user = new HashMap<>();
			user.put("name", c.get("name").toString());
			user.put("planCount", c.get("count").toString());
			for (int i = 0; i < todoChart.size(); i++) {
				if (todoChart.get(i).get("id") != null && todoChart.get(i).get("id").equals(c.get("id"))) {
					user.put("todoCount", todoChart.get(i).get("count").toString());
					for (int j = 0; j < successTodoChart.size(); j++) {
						if (successTodoChart.get(j).get("id").equals(c.get("id"))) {
							user.put("successTodoCount", successTodoChart.get(j).get("count").toString());
							break;
						} else {
							user.put("successTodoCount", null);
						}
					}
					break;
				} else {
					user.put("todoCount", null);
				}
			}
			result.add(user);
		});
		return result;
	}

	@Memo("자신의 모든 일정 가져오기")
	public List<PlanByUser> getPlanByMy(String id, String search, String code, Pageable pageable, GetType type) {
		if (search == null && code == null) {
			return planByUserAPI.findByStateAndUser_IdOrderByTeam_CodeDesc(BooleanState.YES, id, pageable);
		}

		if (type == GetType.FINISHED) {
			return planByUserAPI.findByStateAndUser_IdAndTeam_CodeAndEndLessThan(BooleanState.YES, id, code, new Date(),
				pageable);
		}
		if (code == null)
			return planByUserAPI.findByStateAndUser_IdAndTagContainsIgnoreCaseOrderBySeq(BooleanState.YES, id, search, pageable);
		return planByUserAPI.findByStateAndUser_IdAndTeam_CodeAndTagContainsIgnoreCaseOrderBySeq(BooleanState.YES, id, code,
			search, pageable);
	}

	@Memo("자신의 모든 일정 개수 가져오기")
	public long countPlanByMy(String id, String search, String code, GetType type) {
		if (search == null && code == null) {
			return planByUserAPI.countByStateAndUser_Id(BooleanState.YES, id);
		}

		if (type == GetType.FINISHED) {
			return planByUserAPI.countByStateAndUser_IdAndTeam_CodeAndEndLessThan(BooleanState.YES, id, code, new Date());
		}
		if (code == null)
			return planByUserAPI.countByStateAndUser_IdAndTagContainsIgnoreCaseOrderBySeq(BooleanState.YES, id, search);
		return planByUserAPI.countByStateAndUser_IdAndTeam_CodeAndTagContainsIgnoreCaseOrderBySeq(BooleanState.YES, id, code,
			search);
	}

	@Memo("해당 코드의 팀 일정 중 내 일정만 가져오는 메소드")
	public List<PlanByUser> getMyPlanFromTeam(String code, String id, Pageable pageable) {
		return planByUserAPI.findByStateAndTeam_CodeAndUser_IdOrderByUser_IdDesc(BooleanState.YES, code, id, pageable);
	}

	@Memo("해당 코드의 팀 일정 중 내 일정의 개수를 모두 가져오는 메소드")
	public long getMyPlanCountFromTeam(String code, String id) {
		return planByUserAPI.countByStateAndTeam_CodeAndUser_Id(BooleanState.YES, code, id);
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
			return planByUserAPI.findByStateAndTeam_CodeOrderByUser_IdDesc(BooleanState.YES, code, pageable);
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

	@Memo("엑셀 양식 다운로드")
	public Resource excelFormDown() {
		return commonFileUpload.excelDown();
	}

	public enum GetType {
		SEARCH, FINISHED, NON
	}
}
