package ljy.book.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.SaveState;
import ljy.book.admin.entity.enums.ClassType;
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

	String name;

	@Column(nullable = false)
	String startDate;

	@Column(nullable = false)
	String endDate;

	@Lob
	String content;

	String plannerDocName;

	@Enumerated(EnumType.STRING)
	ClassType type;

	@Enumerated(EnumType.STRING)
	BooleanState replyPermit_state;

	@Column(nullable = false)
	String selectMenu;

	@Enumerated(EnumType.STRING)
	BooleanState use_state;

	@Enumerated(EnumType.STRING)
	SaveState saveState;

	@ManyToOne(fetch = FetchType.LAZY)
	KM_user kmUser;
}
