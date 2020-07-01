package ljy_yjw.team_manage.system.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Alias("JoinTeam")
public class JoinTeam {

	@Id
	@GeneratedValue
	long seq;

	@Temporal(TemporalType.DATE)
	@JsonInclude(value = Include.NON_NULL)
	Date date;

	@Enumerated(EnumType.STRING)
	@JsonInclude(value = Include.NON_NULL)
	BooleanState state;

	String reson;

	@ManyToOne
	@JsonInclude(value = Include.NON_NULL)
	@JsonManagedReference
	Users user;

	@ManyToOne
	@JsonBackReference
	Team team;

}
