package ljy.book.admin.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.enums.UserRule;
import ljy.book.admin.jpaAPI.KM_UserAPI;

@Service
@Transactional
public class KM_UserService implements UserDetailsService {

	@Autowired
	KM_UserAPI km_userAPI;

	@Autowired
	PasswordEncoder passwordEncoder;

	public KM_user save(KM_user user) {
		user.setPass(passwordEncoder.encode(user.getPass()));
		return km_userAPI.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		KM_user user = km_userAPI.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return new User(user.getId(), user.getPass(), authorities(user.getUserRule()));
	}

	private Collection<? extends GrantedAuthority> authorities(Set<UserRule> roles) {
		return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.name())).collect(Collectors.toSet());
	}

}
