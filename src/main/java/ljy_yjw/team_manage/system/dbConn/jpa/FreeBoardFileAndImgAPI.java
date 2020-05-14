package ljy_yjw.team_manage.system.dbConn.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy_yjw.team_manage.system.domain.entity.BoardFileAndImg;

public interface FreeBoardFileAndImgAPI extends JpaRepository<BoardFileAndImg, Long> {

}
