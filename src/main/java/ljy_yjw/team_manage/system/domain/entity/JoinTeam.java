package ljy_yjw.team_manage.system.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.mapping.Array;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

	@JsonInclude(value = Include.NON_NULL)
	String reson;

	@ManyToOne
	@JsonInclude(value = Include.NON_NULL)
	@JsonManagedReference
	Users user;

	@ManyToOne
	@JsonBackReference
	Team team;

	@JsonIgnore
	public static List<JoinTeam> getRealJoinPerson(List<JoinTeam> joinPerson) {
		List<JoinTeam> returnList = new ArrayList<JoinTeam>();
		for (int i = 0; i < joinPerson.size(); i++) {
			JoinTeam person = joinPerson.get(i);
			if (person.state == BooleanState.YES)
				returnList.add(person);
		}
		return returnList;
	}
}
