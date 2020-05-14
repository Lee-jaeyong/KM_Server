package ljy_yjw.team_manage.system.service.insert.freeBoard;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.CustomDate;
import ljy_yjw.team_manage.system.dbConn.jpa.FreeBoardAPI;
import ljy_yjw.team_manage.system.dbConn.jpa.FreeBoardFileAndImgAPI;
import ljy_yjw.team_manage.system.domain.entity.BoardFileAndImg;
import ljy_yjw.team_manage.system.domain.entity.FreeBoard;
import ljy_yjw.team_manage.system.domain.enums.FileType;
import ljy_yjw.team_manage.system.service.CommonFileUpload;

@Service
public class FreeBoardOneInsertService {

	@Autowired
	FreeBoardAPI freeBoardAPI;

	@Autowired
	FreeBoardFileAndImgAPI freeBoardFileAndImgAPI;
	
	@Autowired
	CommonFileUpload commonFileUpload;

	@Transactional
	public FreeBoard insertFreeBoard(FreeBoard freeBoard) {
		return freeBoardAPI.save(freeBoard);
	}

	@Memo("파일 업로드")
	@Transactional
	public List<String> fileUpload(MultipartFile[] file, long seq, FileType fileType) {
		List<String> saveFiles = new ArrayList<String>();
		for (MultipartFile f : file) {
			String save = commonFileUpload.storeFile(f, "freeBoard", seq);
			if (save != null)
				saveFiles.add(save);
		}
		if (saveFiles.size() != 0) {
			for (String save : saveFiles) {
				BoardFileAndImg saveFile = BoardFileAndImg.builder().name(save).type(fileType)
					.freeBoard(FreeBoard.builder().seq(seq).build()).date(CustomDate.getNowDate()).build();
				freeBoardFileAndImgAPI.save(saveFile);
			}
		}
		return saveFiles;
	}
}
