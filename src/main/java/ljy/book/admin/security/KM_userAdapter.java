package ljy.book.admin.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.enums.UserRule;
	
public class KM_userAdapter extends User {

	KM_user km_user;

	public KM_userAdapter(KM_user km_user) {
		super(km_user.getId(), km_user.getPass(), authorities(km_user.getUserRule()));
		this.km_user = km_user;
	}

	public KM_user getKm_user() {
		return km_user;
	}

	private static Collection<? extends GrantedAuthority> authorities(Set<UserRule> roles) {
		return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.name())).collect(Collectors.toSet());
	}
}
