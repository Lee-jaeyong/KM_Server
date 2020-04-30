package ljy.book.admin.jpaAPI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.Notice;
import ljy.book.admin.entity.enums.BooleanState;

public interface NoticeAPI extends JpaRepository<Notice, Long> {
	Notice findBySeqAndUser_Id(long seq, String id);

	Page<Notice> findByTeam_CodeAndState(String code, BooleanState booleanState, Pageable pageable);

	long countByTeam_CodeAndState(String code, BooleanState booleanState);

	Notice findBySeqAndState(long seq, BooleanState booleanState);
}
