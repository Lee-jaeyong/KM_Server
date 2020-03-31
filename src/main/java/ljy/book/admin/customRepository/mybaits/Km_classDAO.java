package ljy.book.admin.customRepository.mybaits;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Km_classDAO {
	void update(HashMap<String, String> map);
}
