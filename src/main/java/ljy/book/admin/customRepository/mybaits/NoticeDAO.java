package ljy.book.admin.customRepository.mybaits;

import ljy.book.admin.professor.requestDTO.NoticeDTO;

public interface NoticeDAO {
	void update(NoticeDTO notice);

	void delete(long seq);
}
