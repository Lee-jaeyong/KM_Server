package ljy_yjw.team_manage.system.service.read.referenceData;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.ReferenceDataAPI;
import ljy_yjw.team_manage.system.domain.entity.ReferenceData;
import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.service.CommonFileUpload;

@Service
public class ReferenceDataReadService {

	@Autowired
	ReferenceDataAPI referenceDataAPI;

	@Autowired
	CommonFileUpload commonFileUpload;

	public ReferenceData getReferenceData(long seq) throws NotFoundException {
		ReferenceData referenceData = referenceDataAPI.findBySeqAndState(seq, BooleanState.YES);
		if (referenceData == null)
			throw new NotFoundException("해당 번호의 자유게시판이 존재하지 않습니다.");
		return referenceData;
	}

	public List<ReferenceData> getReferenceDataList(String code, Pageable pageable) {
		return referenceDataAPI.findByTeam_CodeAndState(code, BooleanState.YES, pageable);
	}

	public long countReferenceData(String code) {
		return referenceDataAPI.countByTeam_CodeAndState(code, BooleanState.YES);
	}

	@Memo("파일 다운로드")
	public Resource fileDownload(long seq, String fileName) {
		return commonFileUpload.loadFileAsResource(seq, "referenceData", fileName);
	}
}
