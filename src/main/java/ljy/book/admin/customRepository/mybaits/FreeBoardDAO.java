package ljy.book.admin.customRepository.mybaits;

import java.util.HashMap;

import ljy.book.admin.professor.requestDTO.FreeBoardDTO;

public interface FreeBoardDAO {
	void update(FreeBoardDTO freeboardDTO);

	void delete(long seq);

	void fileDelete(HashMap<String, Object> map);
}
