package ljy_yjw.team_manage.system.service.update.referenceData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.mybatis.ReferenceDataDAO;
import ljy_yjw.team_manage.system.domain.entity.ReferenceData;

@Service
public class ReferenceDataOneUpdateService {

	@Autowired
	ReferenceDataDAO referenceDataDAO;

	public ReferenceData updateReferenceData(long seq, ReferenceData referenceData) {
		referenceData.setSeq(seq);
		referenceDataDAO.update(referenceData);
		return referenceData;
	}
}
