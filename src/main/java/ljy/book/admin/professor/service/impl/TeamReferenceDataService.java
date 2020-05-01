package ljy.book.admin.professor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ljy.book.admin.common.object.CustomDate;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.customRepository.mybaits.FreeBoardDAO;
import ljy.book.admin.customRepository.mybaits.ReferenceDataDAO;
import ljy.book.admin.entity.BoardFileAndImg;
import ljy.book.admin.entity.FreeBoard;
import ljy.book.admin.entity.ReferenceData;
import ljy.book.admin.entity.ReferenceFileAndImg;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.FileType;
import ljy.book.admin.jpaAPI.FreeBoardAPI;
import ljy.book.admin.jpaAPI.ReferenceDataAPI;
import ljy.book.admin.jpaAPI.ReferenceDataFileAndImgAPI;
import ljy.book.admin.professor.requestDTO.FreeBoardDTO;
import ljy.book.admin.professor.requestDTO.ReferenceDataDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;

@Service
public class TeamReferenceDataService {

	@Autowired
	ReferenceDataAPI referenceDataAPI;

	@Autowired
	ReferenceDataDAO referenceDataDAO;

	@Autowired
	ReferenceDataFileAndImgAPI referenceDataFileAndImgAPI;

	@Autowired
	CommonFileUpload commonFileUpload;

	public ReferenceData getOne(long seq) {
		return referenceDataAPI.findBySeqAndState(seq, BooleanState.YES);
	}

	@Cacheable(key = "#code", value = "referenceDataList")
	public Page<ReferenceData> getList(String code, Pageable pageable) {
		return referenceDataAPI.findByTeam_CodeAndState(code, BooleanState.YES, pageable);
	}

	@CacheEvict(key = "#team.code", value = "noticeList")
	public ReferenceData save(ReferenceDataDTO freeBoard, TeamDTO team, Users user) {
		Users saveUser = new Users();
		saveUser.setSeq(user.getSeq());
		Team saveTeam = new Team();
		saveTeam.setSeq(team.getSeq());
		ReferenceData saveBoard = ReferenceData.builder().title(freeBoard.getTitle()).content(freeBoard.getContent())
			.date(CustomDate.getNowDate()).state(BooleanState.YES).user(saveUser).team(saveTeam).build();
		return referenceDataAPI.save(saveBoard);
	}

	@CacheEvict(value = "noticeList", allEntries = true)
	public boolean update(ReferenceDataDTO referenceData) {
		referenceDataDAO.update(referenceData);
		return true;
	}

	@CacheEvict(value = "noticeList", allEntries = true)
	public boolean delete(long seq) {
		referenceDataDAO.delete(seq);
		return true;
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

	@Memo("파일 삭제")
	@Transactional
	public boolean fileDelete(String fileName, long seq) {
		if (commonFileUpload.deleteFile(seq, "referenceData", fileName)) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("fileName", fileName);
			map.put("seq", seq);
			referenceDataDAO.fileDelete(map);
		}
		return true;
	}

	@Memo("파일 다운로드")
	public Resource fileDownload(long seq, String fileName) {
		return commonFileUpload.loadFileAsResource(seq, "referenceData", fileName);
	}
}
