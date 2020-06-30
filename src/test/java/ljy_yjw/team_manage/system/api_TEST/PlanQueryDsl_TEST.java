package ljy_yjw.team_manage.system.api_TEST;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import ljy_yjw.team_manage.system.CommonTestConfig;
import ljy_yjw.team_manage.system.dbConn.queryDsl.plan.PlanQueryDsl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanQueryDsl_TEST extends CommonTestConfig {

	@Autowired
	PlanQueryDsl planQueryDsl;

	@Test
	public void test_1() throws Exception {
	}
}
