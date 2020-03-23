package ljy.book.admin.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class KM_signUpClassForStu {

	@Id
	@GeneratedValue
	Long seq;
	
	boolean signUp_state;
	
	@Temporal(TemporalType.DATE)
	Date date;
}
