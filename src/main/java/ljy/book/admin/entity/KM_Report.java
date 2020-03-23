package ljy.book.admin.entity;

import java.util.Date;

import javax.persistence.Column;
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
@Setter
@Getter
@NoArgsConstructor
public class KM_Report {

	@Id
	@GeneratedValue
	Long seq;

	@Column(nullable = false)
	String name;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	Date endDate;

	@Lob
	String content = "";

	long hit = 0;

	boolean submitOverDue_state = true;

	boolean showOtherReportOfStu_state = true;
}
