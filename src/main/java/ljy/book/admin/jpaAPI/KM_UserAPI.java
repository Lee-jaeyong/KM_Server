package ljy.book.admin.jpaAPI;

import ljy.book.admin.common.repository.CommonRepository;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.repository.projection.Km_userProjection;

public interface KM_UserAPI extends CommonRepository<KM_user, Long> {

//	Km_userProjection findByIdAndPass(String id, String pass);
//
//	KM_user findById(String id);
}
