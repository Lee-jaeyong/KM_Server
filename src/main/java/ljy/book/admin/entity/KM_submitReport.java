package ljy.book.admin.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class KM_submitReport {

	@Id
	@GeneratedValue
	Long seq;

	@Lob
	String title;

	@Lob
	String content;

	@Temporal(TemporalType.DATE)
	Date date;

	boolean show_state;

	boolean showOtherStu_state;

	boolean saveState;
}
