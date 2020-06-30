package ljy_yjw.team_manage.system.domain.entity;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import ljy_yjw.team_manage.system.domain.enums.BooleanState;
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
@Builder
@Alias("Team")
public class Team {

	@Id
	@GeneratedValue
	@JsonIgnore
	long seq;

	@Column(length = 20)
	String code;

	@Column(nullable = false, length = 100)
	String name;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	Date startDate;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	Date endDate;

	@Column(nullable = false)
	@Lob
	String description;

	@Enumerated(EnumType.STRING)
	@JsonIgnore
	BooleanState flag;

	@ManyToOne(fetch = FetchType.LAZY)
	Users teamLeader;

	@OneToMany(mappedBy = "team")
	@JsonManagedReference
	@JsonInclude(value = Include.NON_EMPTY)
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
