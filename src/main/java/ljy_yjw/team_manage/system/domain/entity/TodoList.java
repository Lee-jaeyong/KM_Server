package ljy_yjw.team_manage.system.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.ibatis.type.Alias;
import org.junit.Ignore;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ljy_yjw.team_manage.system.domain.enums.BooleanState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("TodoList")
public class TodoList {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	String title;

	@Enumerated(EnumType.STRING)
	@JsonIgnore
	BooleanState state = BooleanState.YES;

	@Enumerated(EnumType.STRING)
	BooleanState ing = BooleanState.YES;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	PlanByUser planByUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	Users user;
}
