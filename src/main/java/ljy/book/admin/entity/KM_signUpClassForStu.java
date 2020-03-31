package ljy.book.admin.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ljy.book.admin.entity.enums.BooleanState;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class KM_signUpClassForStu {

	@Id
	@GeneratedValue
	Long seq;

	@Enumerated(EnumType.STRING)
	BooleanState signUpState;

	String date;

	@ManyToOne(fetch = FetchType.LAZY)
	KM_class kmClass;

	@ManyToOne(fetch = FetchType.EAGER)
	KM_user kmUser;
}
