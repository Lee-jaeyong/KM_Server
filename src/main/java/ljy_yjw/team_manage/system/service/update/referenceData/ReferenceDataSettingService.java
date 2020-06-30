package ljy_yjw.team_manage.system.service.update.referenceData;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.entity.ReferenceData;
import ljy_yjw.team_manage.system.domain.entity.ReferenceFileAndImg;
import ljy_yjw.team_manage.system.service.read.referenceData.ReferenceDataReadService;

@Service
public class ReferenceDataSettingService {

	@Autowired
	ReferenceDataReadService referenceDataReadService;

	@Memo("참고자료의 이미지 파일을 모두 바이트 형태로 변환")
	public void settingImg(ReferenceData referenceData) throws IOException {
		List<ReferenceFileAndImg> list = referenceData.getFileList();
		for (ReferenceFileAndImg c : list) {
			c.getImgByte(referenceDataReadService);
		}
	}
}
