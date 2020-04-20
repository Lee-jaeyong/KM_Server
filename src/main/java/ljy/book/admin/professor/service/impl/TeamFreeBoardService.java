package ljy.book.admin.professor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy.book.admin.common.object.CustomDate;
import ljy.book.admin.customRepository.mybaits.FreeBoardDAO;
import ljy.book.admin.entity.FreeBoard;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.jpaAPI.FreeBoardAPI;
import ljy.book.admin.professor.requestDTO.FreeBoardDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;

@Service
public class TeamFreeBoardService {

	@Autowired
	FreeBoardAPI freeBoardAPI;

	@Autowired
	FreeBoardDAO freeBoardDAO;

	public FreeBoard getOne(long seq) {
		return freeBoardAPI.findBySeqAndState(seq, BooleanState.YSE);
	}

	public FreeBoard save(FreeBoardDTO freeBoard, TeamDTO team, Users user) {
		Users saveUser = new Users();
		saveUser.setSeq(user.getSeq());
		Team saveTeam = new Team();
		saveTeam.setSeq(team.getSeq());
		FreeBoard saveBoard = FreeBoard.builder().title(freeBoard.getTitle()).content(freeBoard.getContent())
			.date(CustomDate.getNowDate()).state(BooleanState.YSE).user(saveUser).team(saveTeam).build();
		return freeBoardAPI.save(saveBoard);
	}

	public boolean update(FreeBoardDTO freeBoard) {
		freeBoardDAO.update(freeBoard);
		return true;
	}

	public boolean delete(long seq) {
		freeBoardDAO.delete(seq);
		return true;
	}
}
