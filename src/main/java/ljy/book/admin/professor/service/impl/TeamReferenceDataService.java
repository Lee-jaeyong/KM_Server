package ljy.book.admin.professor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ljy.book.admin.common.object.CustomDate;
import ljy.book.admin.customRepository.mybaits.FreeBoardDAO;
import ljy.book.admin.customRepository.mybaits.ReferenceDataDAO;
import ljy.book.admin.entity.FreeBoard;
import ljy.book.admin.entity.ReferenceData;
import ljy.book.admin.entity.Team;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.jpaAPI.FreeBoardAPI;
import ljy.book.admin.jpaAPI.ReferenceDataAPI;
import ljy.book.admin.professor.requestDTO.FreeBoardDTO;
import ljy.book.admin.professor.requestDTO.ReferenceDataDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;

@Service
public class TeamReferenceDataService {

	@Autowired
	ReferenceDataAPI referenceDataAPI;

	@Autowired
	ReferenceDataDAO referenceDataDAO;

	public ReferenceData getOne(long seq) {
		return referenceDataAPI.findBySeqAndState(seq, BooleanState.YSE);
	}

	public Page<ReferenceData> getList(String code, Pageable pageable) {
		return referenceDataAPI.findByTeam_CodeAndState(code, BooleanState.YSE, pageable);
	}

	public ReferenceData save(ReferenceDataDTO freeBoard, TeamDTO team, Users user) {
		Users saveUser = new Users();
		saveUser.setSeq(user.getSeq());
		Team saveTeam = new Team();
		saveTeam.setSeq(team.getSeq());
		ReferenceData saveBoard = ReferenceData.builder().title(freeBoard.getTitle()).content(freeBoard.getContent())
			.date(CustomDate.getNowDate()).state(BooleanState.YSE).user(saveUser).team(saveTeam).build();
		return referenceDataAPI.save(saveBoard);
	}

	public boolean update(ReferenceDataDTO referenceData) {
		referenceDataDAO.update(referenceData);
		return true;
	}

	public boolean delete(long seq) {
		referenceDataDAO.delete(seq);
		return true;
	}
}
