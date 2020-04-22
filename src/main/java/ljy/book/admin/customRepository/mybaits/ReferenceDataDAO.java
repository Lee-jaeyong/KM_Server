package ljy.book.admin.customRepository.mybaits;

import java.util.HashMap;

import ljy.book.admin.professor.requestDTO.ReferenceDataDTO;

public interface ReferenceDataDAO {
	void update(ReferenceDataDTO freeboardDTO);

	void delete(long seq);

	void fileDelete(HashMap<String, Object> map);
}
