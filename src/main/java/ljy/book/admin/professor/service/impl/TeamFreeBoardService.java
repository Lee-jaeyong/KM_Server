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
import ljy.book.admin.entity.BoardFileAndImg;
import ljy.book.admin.entity.FreeBoard;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.FileType;
import ljy.book.admin.jpaAPI.FreeBoardAPI;
import ljy.book.admin.jpaAPI.FreeBoardFileAndImgAPI;
import ljy.book.admin.professor.requestDTO.FreeBoardDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;

@Service
public class TeamFreeBoardService {

	@Autowired
	FreeBoardAPI freeBoardAPI;

	@Autowired
	FreeBoardDAO freeBoardDAO;

	@Autowired
	FreeBoardFileAndImgAPI freeBoardFileAndImgAPI;

	@Autowired
	CommonFileUpload commonFileUpload;

	public FreeBoard getOne(long seq) {
		return freeBoardAPI.findBySeqAndState(seq, BooleanState.YES);
	}

	@Cacheable(key = "#code", value = "freeBoardList")
	public Page<FreeBoard> getList(String code, Pageable pageable) {
		return freeBoardAPI.findByTeam_CodeAndState(code, BooleanState.YES, pageable);
	}

	@CacheEvict(key = "#team.code", value = "freeBoardList")
	public FreeBoard save(FreeBoardDTO freeBoard, TeamDTO team, Users user) {
		Users saveUser = new Users();
		saveUser.setSeq(user.getSeq());
		Team saveTeam = new Team();
		saveTeam.setSeq(team.getSeq());
		FreeBoard saveBoard = FreeBoard.builder().title(freeBoard.getTitle()).content(freeBoard.getContent())
			.date(CustomDate.getNowDate()).state(BooleanState.YES).user(saveUser).team(saveTeam).build();
		return freeBoardAPI.save(saveBoard);
	}

	@CacheEvict(value = "freeBoardList", allEntries = true)
	public boolean update(FreeBoardDTO freeBoard) {
		freeBoardDAO.update(freeBoard);
		return true;
	}

	@CacheEvict(value = "freeBoardList", allEntries = true)
	public boolean delete(long seq) {
		freeBoardDAO.delete(seq);
		return true;
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

	@Memo("파일 삭제")
	@Transactional
	public boolean fileDelete(String fileName, long seq) {
		if (commonFileUpload.deleteFile(seq, "freeBoard", fileName)) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("fileName", fileName);
			map.put("seq", seq);
			freeBoardDAO.fileDelete(map);
		}
		return true;
	}

	@Memo("파일 다운로드")
	public Resource fileDownload(long seq, String fileName) {
		return commonFileUpload.loadFileAsResource(seq, "freeBoard", fileName);
	}
}
