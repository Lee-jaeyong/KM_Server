package ljy.book.admin.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ljy.book.admin.dto.Km_userLoginDTO;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.jpaAPI.KM_UserAPI;
import ljy.book.admin.repository.projection.Km_userProjection;

@Service
@Transactional
public class KM_UserService {

	@Autowired
	KM_UserAPI km_userAPI;

	public Km_userProjection login(Km_userLoginDTO user) throws JsonProcessingException {
		//return km_userAPI.findByIdAndPass(user.getId(), user.getPass());
		return null;
	}

	public KM_user findById(KM_user km_user) {
		return null;
		//return km_userAPI.findById(km_user.getId());
	}
}
