package ljy_yjw.team_manage.system.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Alias("PlanByUser")
public class PlanByUser {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	String tag;

	@Column(nullable = false)
	String content;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	Date start;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	Date end;

	@Column(nullable = false)
	byte progress = 0;

	@Enumerated(EnumType.STRING)
	BooleanState teamPlan = BooleanState.NO;

	@Enumerated(EnumType.STRING)
	BooleanState state = BooleanState.YES;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonManagedReference
	Users user;

	@ManyToOne
	@JsonIgnore
	Team team;
}
