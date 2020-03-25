package ljy.book.admin.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ljy.book.admin.entity.enums.BooleanState;
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

	@Column(nullable = false)
	String startDate;

	@Column(nullable = false)
	String endDate;

	@Lob
	String content = "";

	long hit = 0;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	BooleanState submitOverDue_state;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	BooleanState showOtherReportOfStu_state;

	@ManyToOne(fetch = FetchType.LAZY)
	KM_class kmClass;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "kmReport")
	List<KM_fileAndImgOfReport> kmFileAndImgOfReport = new ArrayList<KM_fileAndImgOfReport>();

	public void addKmFileAndImgOfReport(KM_fileAndImgOfReport km_fileAndImgOfReport) {
		this.kmFileAndImgOfReport.add(km_fileAndImgOfReport);
		km_fileAndImgOfReport.setKmReport(this);
	}
}
