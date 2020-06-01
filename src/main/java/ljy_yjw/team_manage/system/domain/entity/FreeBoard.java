package ljy_yjw.team_manage.system.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Alias("FreeBoard")
public class FreeBoard {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	@JsonInclude(value = Include.NON_NULL)
	@Lob
	String title;

	@Column(nullable = false)
	@JsonInclude(value = Include.NON_NULL)
	@Lob
	String content;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonInclude(value = Include.NON_NULL)
	Date date;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@JsonInclude(value = Include.NON_NULL)
	BooleanState state;

	@ManyToOne
	@JsonManagedReference
	@JsonInclude(value = Include.NON_NULL)
	Users user;

	@ManyToOne
	@JsonIgnore
	Team team;

	@OneToMany(mappedBy = "freeBoard")
	@JsonManagedReference
	@JsonInclude(value = Include.NON_NULL)
	List<BoardFileAndImg> fileList = new ArrayList<BoardFileAndImg>();
}
