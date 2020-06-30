package ljy_yjw.team_manage.system.security;

import java.io.IOException;
import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.dbConn.jpa.UsersAPI;
import ljy_yjw.team_manage.system.dbConn.mybatis.UserDAO;
import ljy_yjw.team_manage.system.domain.dto.UserDTO;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.service.CommonFileUpload;

@Service
@Transactional
public class UsersService implements UserDetailsService {

	@Autowired
	UserDAO userDAO;

	@Autowired
	UsersAPI userAPI;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CommonFileUpload commonFileUpload;

	public boolean checkDupId(String id) {
		return userAPI.findById(id).isPresent();
	}

	public Users findByUserId(String id) {
		return userAPI.findById(id).get();
	}

	public Users save(UserDTO user) {
		Users saveUser = user.parseThis2User(passwordEncoder);
		return userAPI.save(saveUser);
	}

	public UserDTO update(UserDTO user) {
		userDAO.update(user);
		return user;
	}

	public boolean delete(Users user) {
		UserDTO deleteUser = new UserDTO();
		deleteUser.setId(user.getId());
		userDAO.delete(user);
		return true;
	}

	@Memo("파일 업로드")
	@Transactional
	public String fileUpload(MultipartFile file, long seq, String id) {
		String save = commonFileUpload.storeFile(file, "user", seq);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("img", save);
		userDAO.insertImage(map);
		return save;
	}

	@Memo("파일 다운로드")
	public Resource fileDownload(long seq, String fileName) {
		return commonFileUpload.loadFileAsResource(seq, "user", fileName);
	}

	@Memo("파일 삭제")
	@Transactional
	public boolean fileDelete(String fileName, long seq) throws IOException {
		if (commonFileUpload.deleteFile(seq, "user", fileName)) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("fileName", fileName);
			map.put("seq", seq);
			userDAO.fileDelete(map);
		}
		return true;
	}

	@Memo("로그인")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userAPI.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return new UserAdapter(user);
	}

}
