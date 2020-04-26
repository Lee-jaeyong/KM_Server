package ljy.book.admin.jpaAPI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.JoinTeam;
import ljy.book.admin.entity.enums.BooleanState;

public interface TeamJoinRequestAPI extends JpaRepository<JoinTeam, Long> {
	JoinTeam findByTeam_CodeAndUser_Id(String code, String id);

	JoinTeam findBySeqAndTeam_TeamLeader_Id(long seq, String id);

	Page<JoinTeam> findByStateAndTeam_SeqAndResonIsNull(BooleanState state, long seq, Pageable page);
}
