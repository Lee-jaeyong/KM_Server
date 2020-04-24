package ljy.book.admin.security;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ljy.book.admin.customRepository.mybaits.UserDAO;
import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.UserRule;
import ljy.book.admin.jpaAPI.UsersAPI;
import ljy.book.admin.professor.requestDTO.UserDTO;

@Service
@Transactional
public class UsersService implements UserDetailsService {

	@Autowired
	UserDAO userDAO;

	@Autowired
	UsersAPI userAPI;

	@Autowired
	PasswordEncoder passwordEncoder;

	public boolean checkDupId(String id) {
		return userAPI.findById(id).isPresent();
	}

	public Users findByUserId(String id) {
		return userAPI.findById(id).get();
	}

	public Users save(UserDTO user) {
		Users saveUser = new Users();
		saveUser.setId(user.getId());
		saveUser.setName(user.getName());
		saveUser.setPass(passwordEncoder.encode(user.getPass()));
		saveUser.setDate("2020-04-16");
		saveUser.setFlag(BooleanState.YES);
		saveUser.setEmail(user.getEmail());
		Set<UserRule> rule = new HashSet<UserRule>();
		rule.add(UserRule.USER);
		saveUser.setUserRule(rule);
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userAPI.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return new UserAdapter(user);
	}

}
