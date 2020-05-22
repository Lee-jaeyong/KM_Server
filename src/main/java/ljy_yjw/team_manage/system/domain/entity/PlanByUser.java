package ljy_yjw.team_manage.system.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Alias("PlanByUser")
public class PlanByUser {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	@JsonInclude(value = Include.NON_NULL)
	String tag;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonInclude(value = Include.NON_NULL)
	Date start;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonInclude(value = Include.NON_NULL)
	Date end;

	@Enumerated(EnumType.STRING)
	@JsonInclude(value = Include.NON_NULL)
	BooleanState teamPlan = BooleanState.NO;

	@Enumerated(EnumType.STRING)
	@JsonInclude(value = Include.NON_NULL)
	BooleanState state = BooleanState.YES;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonManagedReference
	@JsonInclude(value = Include.NON_NULL)
	Users user;

	@ManyToOne
	@JsonIgnore
	@JsonInclude(value = Include.NON_NULL)
	Team team;

	@OneToMany(mappedBy = "planByUser")
	@JsonInclude(value = Include.NON_NULL)
	List<TodoList> todoList = new ArrayList<TodoList>();

	public static List<PlanByUser> getPlanList_TodoListSuccess(List<PlanByUser> list) {
		ArrayList<PlanByUser> result = new ArrayList<PlanByUser>();
		list.forEach(c -> {
			PlanByUser p = c;
			p.setTodoList(TodoList.stateYesFilter(c.getTodoList()));
			result.add(p);
		});
		return result;
	}

	public static List<PlanByUser> getMyPlanList(List<PlanByUser> list, Users user) {
		return list.stream().filter(c -> c.getUser().getId().equals(user.getId())).collect(Collectors.toList());
	}
}
