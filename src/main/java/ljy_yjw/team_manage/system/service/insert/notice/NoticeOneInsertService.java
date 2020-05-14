package ljy_yjw.team_manage.system.service.insert.notice;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.CustomDate;
import ljy_yjw.team_manage.system.dbConn.jpa.NoticeAPI;
import ljy_yjw.team_manage.system.dbConn.jpa.NoticeFileAndImgAPI;
import ljy_yjw.team_manage.system.domain.entity.Notice;
import ljy_yjw.team_manage.system.domain.entity.NoticeFileAndImg;
import ljy_yjw.team_manage.system.domain.enums.FileType;
import ljy_yjw.team_manage.system.service.CommonFileUpload;

@Service
public class NoticeOneInsertService {

	@Autowired
	NoticeAPI noticeAPI;

	@Autowired
	NoticeFileAndImgAPI noticeFileAndImgAPI;
	
	@Autowired
	CommonFileUpload commonFileUpload;

	public Notice insertNotice(Notice notice) {
		return noticeAPI.save(notice);
	}

	@Memo("파일 업로드")
	@Transactional
	public List<String> fileUpload(MultipartFile[] file, long seq, FileType fileType) {
		List<String> saveFiles = new ArrayList<String>();
		for (MultipartFile f : file) {
			String save = commonFileUpload.storeFile(f, "notice", seq);
			if (save != null)
				saveFiles.add(save);
		}
		if (saveFiles.size() != 0) {
			for (String save : saveFiles) {
				NoticeFileAndImg saveFile = NoticeFileAndImg.builder().name(save).type(fileType)
					.notice(Notice.builder().seq(seq).build()).date(CustomDate.getNowDate()).build();
				noticeFileAndImgAPI.save(saveFile);
			}
		}
		return saveFiles;
	}
}
