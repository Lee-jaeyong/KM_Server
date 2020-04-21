package ljy.book.admin.professor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ljy.book.admin.common.object.CustomDate;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.customRepository.mybaits.NoticeDAO;
import ljy.book.admin.entity.Notice;
import ljy.book.admin.entity.NoticeFileAndImg;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.FileType;
import ljy.book.admin.jpaAPI.NoticeAPI;
import ljy.book.admin.jpaAPI.NoticeFileAndImgAPI;
import ljy.book.admin.professor.requestDTO.NoticeDTO;

@Service
public class TeamNoticeService {

	@Autowired
	NoticeAPI noticeAPI;

	@Autowired
	NoticeDAO noticeDAO;

	@Autowired
	NoticeFileAndImgAPI noticeFileAndImgAPI;

	@Autowired
	CommonFileUpload commonFileUpload;

	@Transactional
	@Memo("번호를 통해 공지사항을 가져옴")
	public Notice getOne(long seq) {
		return noticeAPI.findBySeqAndState(seq, BooleanState.YSE);
	}

	@Transactional
	@Memo("해당 팀의 활성화된 모든 공지사항을 가져옴")
	public Page<Notice> getAll(String code, Pageable pageable) {
		return noticeAPI.findByTeam_CodeAndState(code, BooleanState.YSE, pageable);
	}

	@Transactional
	@Memo("해당 팀의 공지사항을 등록")
	public Notice save(NoticeDTO noticeDTO, Team team, Users user) {
		Users saveUser = new Users();
		saveUser.setSeq(user.getSeq());
		Team saveTeam = new Team();
		saveTeam.setSeq(team.getSeq());
		Notice notice = Notice.builder().date(CustomDate.getNowDate()).title(noticeDTO.getTitle()).content(noticeDTO.getContent())
			.user(saveUser).team(saveTeam).state(BooleanState.YSE).build();
		return noticeAPI.save(notice);
	}

	@Transactional
	@Memo("공지사항을 수정")
	public boolean update(long seq, NoticeDTO noticeDTO) {
		noticeDTO.setSeq(seq);
		noticeDAO.update(noticeDTO);
		return true;
	}

	@Transactional
	@Memo("그 공지사항을 등록한 리더가 맞는가를 체크 후 맞다면 리턴")
	public Notice checkAuthSuccessThenGet(long seq, Users user) {
		return noticeAPI.findBySeqAndUser_Id(seq, user.getId());
	}

	@Transactional
	@Memo("공지사항을 삭제")
	public boolean delete(long seq) {
		noticeDAO.delete(seq);
		return true;
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

	@Memo("파일 삭제")
	@Transactional
	public boolean fileDelete(String fileName, long seq) {
		if (commonFileUpload.deleteFile(seq, "notice", fileName)) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("fileName", fileName);
			map.put("seq", seq);
			noticeDAO.fileDelete(map);
		}
		return true;
	}

	@Memo("파일 다운로드")
	public Resource fileDownload(long seq, String fileName) {
		return commonFileUpload.loadFileAsResource(seq, "notice", fileName);
	}
}
