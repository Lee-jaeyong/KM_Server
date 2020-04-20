package ljy.book.admin.professor.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ljy.book.admin.common.object.CustomDate;
import ljy.book.admin.customRepository.mybaits.NoticeDAO;
import ljy.book.admin.entity.Notice;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.jpaAPI.NoticeAPI;
import ljy.book.admin.professor.requestDTO.NoticeDTO;

@Service
public class TeamNoticeService {

	@Autowired
	NoticeAPI noticeAPI;

	@Autowired
	NoticeDAO noticeDAO;

	@Transactional
	public Page<Notice> getAll(String code, Pageable pageable) {
		return noticeAPI.findByTeam_CodeAndState(code, BooleanState.YSE, pageable);
	}

	@Transactional
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
	public boolean update(long seq, NoticeDTO noticeDTO) {
		noticeDTO.setSeq(seq);
		noticeDAO.update(noticeDTO);
		return true;
	}

	@Transactional
	public Notice checkAuthSuccessThenGet(long seq, Users user) {
		return noticeAPI.findBySeqAndUser_Id(seq, user.getId());
	}

	@Transactional
	public boolean delete(long seq) {
		noticeDAO.delete(seq);
		return true;
	}
}
