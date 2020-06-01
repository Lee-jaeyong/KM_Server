package ljy_yjw.team_manage.system.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Alias("Notice")
public class Notice {

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

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@JsonInclude(value = Include.NON_NULL)
	BooleanState state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonManagedReference
	@JsonInclude(value = Include.NON_NULL)
	Users user;

	@ManyToOne
	@JsonIgnore
	Team team;

	@OneToMany(mappedBy = "notice")
	@JsonManagedReference
	@JsonInclude(value = Include.NON_EMPTY)
	List<NoticeFileAndImg> fileList = new ArrayList<NoticeFileAndImg>();
}
