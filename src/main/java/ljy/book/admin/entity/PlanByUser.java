package ljy.book.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

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
@Builder
public class PlanByUser {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	String tag;

	@Column(nullable = false)
	String content;

	@Column(nullable = false)
	String start;

	@Column(nullable = false)
	String end;

	@Column(nullable = false)
	byte progress;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	BooleanState teamPlan;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	BooleanState state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonManagedReference
	Users user;

	@ManyToOne
	@JsonIgnore
	Team team;
}
