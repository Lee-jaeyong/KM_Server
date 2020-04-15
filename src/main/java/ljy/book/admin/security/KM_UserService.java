package ljy.book.admin.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ljy.book.admin.entity.Users;
import ljy.book.admin.jpaAPI.UsersAPI;

@Service
@Transactional
public class KM_UserService implements UserDetailsService {

	@Autowired
	UsersAPI userAPI;

	@Autowired
	PasswordEncoder passwordEncoder;

	public Users findByUserId(String id) {
		return userAPI.findById(id).get();
	}
	
	public Users save(Users user) {
		user.setPass(passwordEncoder.encode(user.getPass()));
		return userAPI.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userAPI.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return new UserAdapter(user);
	}

}
