package ljy.book.admin.customRepository.mybaits;

import org.apache.ibatis.annotations.Mapper;

import ljy.book.admin.professor.requestDTO.TeamDTO;

@Mapper
public interface TeamDAO {
	void update(TeamDTO team);

	void delete(TeamDTO team);

	void updateProgress(TeamDTO team);
}
