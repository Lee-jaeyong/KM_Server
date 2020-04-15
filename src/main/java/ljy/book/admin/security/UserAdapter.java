package ljy.book.admin.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import ljy.book.admin.entity.Users;
import ljy.book.admin.entity.enums.UserRule;
	
public class UserAdapter extends User {

	Users user;

	public UserAdapter(Users user) {
		super(user.getId(), user.getPass(), authorities(user.getUserRule()));
		this.user = user;
	}

	public Users getKm_user() {
		return user;
	}

	private static Collection<? extends GrantedAuthority> authorities(Set<UserRule> roles) {
		return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.name())).collect(Collectors.toSet());
	}
}
