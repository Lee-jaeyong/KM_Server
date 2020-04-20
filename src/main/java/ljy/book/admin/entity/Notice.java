package ljy.book.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Notice {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	String title;

	@Column(nullable = false)
	String content;

	@Column(nullable = false)
	String date;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	BooleanState state;

	@ManyToOne
	@JsonIgnore
	Users user;

	@ManyToOne
	@JsonIgnore
	Team team;
}
