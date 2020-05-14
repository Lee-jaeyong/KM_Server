package ljy_yjw.team_manage.system.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.UserRule;
	
public class UserAdapter extends User {

	public Users user;

	public UserAdapter(Users user) {
		super(user.getId(), user.getPass(), authorities(user.getUserRule()));
		this.user = user;
	}

	private static Collection<? extends GrantedAuthority> authorities(Set<UserRule> roles) {
		return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.name())).collect(Collectors.toSet());
	}
}
