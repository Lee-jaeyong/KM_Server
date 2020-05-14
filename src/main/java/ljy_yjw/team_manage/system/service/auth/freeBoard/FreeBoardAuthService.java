package ljy_yjw.team_manage.system.service.auth.freeBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.dbConn.jpa.auth.AuthFreeBoardAPI;

@Service
public class FreeBoardAuthService {

	@Autowired
	AuthFreeBoardAPI authFreeBoardAPI;

	public void boardAuthCheck(long seq, String id) throws NotFoundException {
		if(!authFreeBoardAPI.existsBySeqAndUser_Id(seq, id)) {
			throw new NotFoundException("권한이 존재하지 않거나 혹은 존재하지 않는 게시판입니다.");
		}
	}
}
