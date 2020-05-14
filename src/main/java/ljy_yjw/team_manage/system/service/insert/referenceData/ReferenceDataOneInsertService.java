package ljy_yjw.team_manage.system.service.insert.referenceData;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.CustomDate;
import ljy_yjw.team_manage.system.dbConn.jpa.ReferenceDataAPI;
import ljy_yjw.team_manage.system.dbConn.jpa.ReferenceDataFileAndImgAPI;
import ljy_yjw.team_manage.system.domain.entity.ReferenceData;
import ljy_yjw.team_manage.system.domain.entity.ReferenceFileAndImg;
import ljy_yjw.team_manage.system.domain.enums.FileType;
import ljy_yjw.team_manage.system.service.CommonFileUpload;

@Service
public class ReferenceDataOneInsertService {

	@Autowired
	ReferenceDataAPI referenceDataAPI;

	@Autowired
	ReferenceDataFileAndImgAPI referenceDataFileAndImgAPI;

	@Autowired
	CommonFileUpload commonFileUpload;

	@Transactional
	public ReferenceData insertReferenceData(ReferenceData referenceData) {
		return referenceDataAPI.save(referenceData);
	}

	@Memo("파일 업로드")
	@Transactional
	public List<String> fileUpload(MultipartFile[] file, long seq, FileType fileType) {
		List<String> saveFiles = new ArrayList<String>();
		for (MultipartFile f : file) {
			String save = commonFileUpload.storeFile(f, "referenceData", seq);
			if (save != null)
				saveFiles.add(save);
		}
		if (saveFiles.size() != 0) {
			for (String save : saveFiles) {
				ReferenceFileAndImg saveFile = ReferenceFileAndImg.builder().name(save).type(fileType)
					.referenceData(ReferenceData.builder().seq(seq).build()).date(CustomDate.getNowDate()).build();
				referenceDataFileAndImgAPI.save(saveFile);
			}
		}
		return saveFiles;
	}
}
