package ljy.book.admin.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class KM_signUpClassForStu {

	@Id
	@GeneratedValue
	Long seq;

	boolean signUp_state;

	String date;

	@ManyToOne(fetch = FetchType.LAZY)
	KM_class kmClass;
}
