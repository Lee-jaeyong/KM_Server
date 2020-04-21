package ljy.book.admin.customRepository.mybaits;

import ljy.book.admin.professor.requestDTO.ReferenceDataDTO;

public interface ReferenceDataDAO {
	void update(ReferenceDataDTO freeboardDTO);

	void delete(long seq);
}
