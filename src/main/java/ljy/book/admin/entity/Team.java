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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ljy.book.admin.entity.enums.BooleanState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	@JsonManagedReference
	List<JoinTeam> joinPerson = new ArrayList<JoinTeam>();

	@OneToMany(mappedBy = "team")
	@JsonIgnore
	List<PlanByUser> planByUser = new ArrayList<PlanByUser>();

	@OneToMany(mappedBy = "team", targetEntity = Notice.class)
	@JsonIgnore
	List<Notice> notice = new ArrayList<Notice>();

	@OneToMany(mappedBy = "team", targetEntity = FreeBoard.class)
	@JsonIgnore
	List<FreeBoard> freeBoard = new ArrayList<FreeBoard>();

	@JsonIgnore
	public void addJoinPerson(JoinTeam joinTeam) {
		this.joinPerson.add(joinTeam);
		joinTeam.setTeam(this);
	}

	@JsonIgnore
	public void addPlan(PlanByUser planByUser) {
		this.planByUser.add(planByUser);
		planByUser.setTeam(this);
	}
}
