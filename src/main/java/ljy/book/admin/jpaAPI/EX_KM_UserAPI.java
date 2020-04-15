package ljy.book.admin.jpaAPI;

import java.util.Optional;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.entity.Users;

public interface EX_KM_UserAPI extends CommonRepository<Users, Long> {

//	Optional<Users> findByIdAndPass(String id, String pass);
//
	Optional<Users> findById(String id);

//	Km_userProjection findByIdAndPass(String id, String pass);
//
//	KM_user findById(String id);
}
