package ljy.book.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@Enumerated(EnumType.STRING)
	BooleanState state;

	String reson;
	
	@ManyToOne
	@JsonManagedReference
	Users user;

	@ManyToOne
	@JsonBackReference
	Team team;
}
