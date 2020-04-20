package ljy.book.admin.professor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy.book.admin.common.object.CustomDate;
import ljy.book.admin.entity.Notice;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.jpaAPI.NoticeAPI;
import ljy.book.admin.professor.requestDTO.NoticeDTO;

@Service
public class TeamNoticeService {

	@Autowired
	NoticeAPI noticeAPI;

	public Notice save(NoticeDTO noticeDTO, Team team, Users user) {
		Users saveUser = new Users();
		saveUser.setSeq(user.getSeq());
		Team saveTeam = new Team();
		saveTeam.setSeq(team.getSeq());
		Notice notice = Notice.builder().date(CustomDate.getNowDate()).title(noticeDTO.getTitle()).content(noticeDTO.getContent())
			.user(saveUser).team(saveTeam).build();
		return noticeAPI.save(notice);
	}
}
