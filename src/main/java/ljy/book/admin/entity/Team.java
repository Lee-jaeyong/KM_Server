package ljy.book.admin.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Team {

	@Id
	@GeneratedValue
	long seq;

	String code;

	@Column(nullable = false)
	String name;
	@Column(nullable = false)
	String startDate;
	@Column(nullable = false)
	String endDate;
	@Column(nullable = false)
	String description;
	byte progress;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	BooleanState flag;

	@ManyToOne
	Users teamLeader;

	@OneToMany(mappedBy = "team")
	List<JoinTeam> joinPerson = new ArrayList<JoinTeam>();

	@OneToMany(mappedBy = "team")
	List<PlanByUser> planByUser = new ArrayList<PlanByUser>();

	@OneToMany(mappedBy = "team", targetEntity = Notice.class)
	List<Notice> notice = new ArrayList<Notice>();

	@OneToMany(mappedBy = "team", targetEntity = FreeBoard.class)
	List<FreeBoard> freeBoard = new ArrayList<FreeBoard>();
}
