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
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.UserRule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users {

	@Id
	@GeneratedValue
	@JsonIgnore
	long seq;

	@Column(nullable = false)
	String id;

	@JsonIgnore
	@Column(nullable = false)
	String pass;

	@Column(nullable = false)
	String name;

	@Column(nullable = false)
	String email;

	@Column(nullable = false)
	String date;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	BooleanState flag;

	String img;

	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	Set<UserRule> userRule;

	@OneToMany(mappedBy = "user")
	List<PlanByUser> plan = new ArrayList<PlanByUser>();

	@OneToMany(mappedBy = "teamLeader")
	List<Team> team = new ArrayList<Team>();

	@OneToMany(mappedBy = "user")
	List<JoinTeam> joinTeam = new ArrayList<JoinTeam>();

	@OneToMany(mappedBy = "user")
	List<Notice> notice = new ArrayList<Notice>();

	@OneToMany(mappedBy = "user")
	List<FreeBoard> freeBoard = new ArrayList<FreeBoard>();

	public void addTeam(Team team) {
		this.team.add(team);
		team.setTeamLeader(this);
	}

	public void addPlan(PlanByUser plan) {
		this.plan.add(plan);
		plan.setUser(this);
	}
}
