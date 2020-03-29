package ljy.book.admin.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ljy.book.admin.entity.enums.UserRule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class KM_user {

	@Id
	@GeneratedValue
	long seq;

	String id;

	String pass;

	String name;

	String email;

	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	Set<UserRule> userRule;

	@OneToMany(mappedBy = "kmUser")
	List<KM_class> kmClass = new ArrayList<KM_class>();

	public void addKmClass(KM_class km_class) {
		this.kmClass.add(km_class);
		km_class.setKmUser(this);
	}

}
