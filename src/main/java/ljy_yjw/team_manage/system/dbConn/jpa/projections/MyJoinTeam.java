package ljy_yjw.team_manage.system.dbConn.jpa.projections;

import java.util.Date;

import ljy_yjw.team_manage.system.domain.enums.BooleanState;

public interface MyJoinTeam {
	long getSeq();

	Date getDate();

	BooleanState getState();

	String getReson();

	String getTeam_Code();
}
