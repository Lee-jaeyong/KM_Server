package ljy.book.admin.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ljy.book.admin.entity.enums.BooleanState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PlanByUser {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	String tag;

	@Column(nullable = false)
	String content;

	@Column(nullable = false)
	String start;

	@Column(nullable = false)
	String end;

	@Column(nullable = false)
	byte progress;

	@Column(nullable = false)
	BooleanState teamPlan;
	
	@ManyToOne
	Users user;

	@ManyToOne
	Team team;

	@OneToMany(mappedBy = "plan")
	List<PlanFileAndImg> fileList = new ArrayList<PlanFileAndImg>();
}
