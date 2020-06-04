package ljy_yjw.team_manage.system.service.read.plan;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.jpa.plan.GetListPlanByUserAPI;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;

@Service
public class PlanSearchService {

	@Autowired
	GetListPlanByUserAPI getListPlanByUserAPI;

	public List<PlanByUser> searchMyPlan(String code, String id, String tag, String title, Date start, Date end,
		Pageable pageable) {
		if (start != null && end != null && !title.equals("")) {
			return getListPlanByUserAPI
				.findByTeam_CodeAndUser_IdAndStateAndTagContainingAndTodoList_TitleContainingAndStartGreaterThanEqualAndEndLessThanEqualOrderBySeqDesc(
					code, id, BooleanState.YES, tag, title, start, end, pageable);
		} else if (start != null && end != null && title.equals("")) {
			return getListPlanByUserAPI
				.findByTeam_CodeAndUser_IdAndStateAndTagContainingAndStartGreaterThanEqualAndEndLessThanEqualOrderBySeqDesc(code,
					id, BooleanState.YES, tag, start, end, pageable);
		} else if (start == null && end == null && !title.equals("")) {
			return getListPlanByUserAPI
				.findByTeam_CodeAndUser_IdAndStateAndTagContainingAndTodoList_TitleContainingOrderBySeqDesc(code, id,
					BooleanState.YES, tag, title, pageable);
		} else {
			return getListPlanByUserAPI.findByTeam_CodeAndUser_IdAndStateAndTagContainingOrderBySeqDesc(code, id,
				BooleanState.YES, tag, pageable);
		}
	}

	public long searchMyPlanCount(String code, String id, String tag, String title, Date start, Date end) {
		if (start != null && end != null && !title.equals("")) {
			return getListPlanByUserAPI
				.countByTeam_CodeAndUser_IdAndStateAndTagContainingAndTodoList_TitleContainingAndStartGreaterThanEqualAndEndLessThanEqual(
					code, id, BooleanState.YES, tag, title, start, end);
		} else if (start != null && end != null && title.equals("")) {
			return getListPlanByUserAPI
				.countByTeam_CodeAndUser_IdAndStateAndTagContainingAndStartGreaterThanEqualAndEndLessThanEqual(code, id,
					BooleanState.YES, tag, start, end);
		} else if (start == null && end == null && !title.equals("")) {
			return getListPlanByUserAPI.countByTeam_CodeAndUser_IdAndStateAndTagContainingAndTodoList_TitleContaining(code, id,
				BooleanState.YES, tag, title);
		} else {
			return getListPlanByUserAPI.countByTeam_CodeAndUser_IdAndStateAndTagContaining(code, id, BooleanState.YES, tag);
		}
	}
}
