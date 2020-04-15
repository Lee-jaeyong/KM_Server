package ljy.book.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ljy.book.admin.entity.enums.BooleanState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class JoinTeam {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	String date;

	@Column(nullable = false)
	BooleanState teamPlan;

	@Column(nullable = false)
	BooleanState state;

	@ManyToOne
	Users user;

	@ManyToOne
	Team team;
}
