package ljy_yjw.team_manage.system.service.delete.referenceData;

import java.io.IOException;
import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.mybatis.ReferenceDataDAO;
import ljy_yjw.team_manage.system.domain.entity.ReferenceData;
import ljy_yjw.team_manage.system.service.CommonFileUpload;

@Service
public class ReferenceDataOneDeleteService {

	@Autowired
	ReferenceDataDAO referenceDataDAO;

	@Autowired
	CommonFileUpload commonFileUpload;

	public ReferenceData deleteReferenceData(long seq) {
		referenceDataDAO.delete(seq);
		return ReferenceData.builder().seq(seq).build();
	}

	@Memo("파일 삭제")
	@Transactional
	public boolean fileDelete(String fileName, long seq) throws IOException {
		if (commonFileUpload.deleteFile(seq, "referenceData", fileName)) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("fileName", fileName);
			map.put("seq", seq);
			referenceDataDAO.fileDelete(map);
		}
		return true;
	}
}
