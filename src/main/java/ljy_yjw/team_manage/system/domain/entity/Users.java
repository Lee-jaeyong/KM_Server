package ljy_yjw.team_manage.system.domain.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import ljy_yjw.team_manage.system.domain.enums.UserRule;
import ljy_yjw.team_manage.system.security.UsersService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("Users")
@ToString
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

	@Transient
	byte[] myImg;

	@JsonIgnore
	@Temporal(TemporalType.DATE)
	@CreatedDate
	Date date = new Date();

	@Enumerated(EnumType.STRING)
	@JsonIgnore
	BooleanState flag = BooleanState.YES;

	@JsonInclude(value = Include.NON_NULL)
	String img;

	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.LAZY)
	@JsonIgnore
	Set<UserRule> userRule;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonBackReference
	List<PlanByUser> plan = new ArrayList<PlanByUser>();

	@OneToMany(mappedBy = "teamLeader")
	@JsonIgnore
	List<Team> team = new ArrayList<Team>();

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	List<JoinTeam> joinTeam = new ArrayList<JoinTeam>();

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	List<Notice> notice = new ArrayList<Notice>();

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	List<FreeBoard> freeBoard = new ArrayList<FreeBoard>();

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	List<ReferenceData> referenceData = new ArrayList<ReferenceData>();

	@JsonIgnore
	public void addTeam(Team team) {
		this.team.add(team);
		team.setTeamLeader(this);
	}

	@JsonIgnore
	public void addPlan(PlanByUser plan) {
		this.plan.add(plan);
		plan.setUser(this);
	}

	@JsonIgnore
	public byte[] getImageByte(UsersService userService) throws IOException {
		if (this.getImg() == null) {
			return null;
		}
		InputStream in = userService.fileDownload(this.getSeq(), this.getImg()).getInputStream();
		return IOUtils.toByteArray(in);
	}
}
