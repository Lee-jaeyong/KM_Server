package ljy.book.admin.jpaAPI;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.FreeBoard;
import ljy.book.admin.entity.enums.BooleanState;

public interface FreeBoardAPI extends JpaRepository<FreeBoard, Long> {
	FreeBoard findBySeqAndState(long seq, BooleanState state);
}
