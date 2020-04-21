package ljy.book.admin.jpaAPI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.ReferenceData;
import ljy.book.admin.entity.enums.BooleanState;

public interface ReferenceDataAPI extends JpaRepository<ReferenceData, Long> {
	ReferenceData findBySeqAndState(long seq, BooleanState state);

	Page<ReferenceData> findByTeam_CodeAndState(String code, BooleanState state, Pageable pageable);
}
