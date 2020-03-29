package ljy.book.admin.jpaAPI;

import java.util.Optional;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.entity.KM_user;

public interface KM_UserAPI extends CommonRepository<KM_user, Long> {

	Optional<KM_user> findByIdAndPass(String id, String pass);

	Optional<KM_user> findById(String id);

//	Km_userProjection findByIdAndPass(String id, String pass);
//
//	KM_user findById(String id);
}
