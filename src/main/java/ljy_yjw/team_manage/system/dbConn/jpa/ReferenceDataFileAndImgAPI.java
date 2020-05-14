package ljy_yjw.team_manage.system.dbConn.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy_yjw.team_manage.system.domain.entity.ReferenceFileAndImg;

public interface ReferenceDataFileAndImgAPI extends JpaRepository<ReferenceFileAndImg, Long> {
}
