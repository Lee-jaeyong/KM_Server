package ljy_yjw.team_manage.system.service.auth.referenceData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.dbConn.jpa.auth.AuthReferenceDataAPI;

@Service
public class ReferenceDataAuthService {

	@Autowired
	AuthReferenceDataAPI authReferenceDataAPI;

	public void boardAuthCheck(long seq, String id) throws NotFoundException {
		if (!authReferenceDataAPI.existsBySeqAndUser_Id(seq, id)) {
			throw new NotFoundException("권한이 존재하지 않거나 혹은 존재하지 않는 참고자료입니다.");
		}
	}
}
