package ljy.book.admin.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.ClassType;
import ljy.book.admin.entity.enums.SaveState;
import ljy.book.admin.entity.enums.SelectClassMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class KM_class {

	@Id
	@GeneratedValue
	Long seq;

	String classCode;

	String name;

	@Lob
	String content;

	String plannerDocName;

	@Enumerated(EnumType.STRING)
	ClassType type;

	@Column(nullable = false)
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	Set<SelectClassMenu> selectMenu;

	@Enumerated(EnumType.STRING)
	BooleanState use_state = BooleanState.YSE;

	@ManyToOne(fetch = FetchType.LAZY)
	KM_user kmUser;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "kmClass")
	List<KM_Report> kmReport = new ArrayList<KM_Report>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "kmClass")
	@JsonManagedReference
	List<KM_signUpClassForStu> kmSignUpClassForStu = new ArrayList<KM_signUpClassForStu>();

	public void addKmSignUpClassForStu(KM_signUpClassForStu signUp) {
		this.kmSignUpClassForStu.add(signUp);
		signUp.setKmClass(this);
	}

	public void addKmReport(KM_Report km_report) {
		this.kmReport.add(km_report);
		km_report.setKmClass(this);
	}
}
