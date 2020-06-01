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
@Alias("ReferenceData")
public class ReferenceData {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	@Lob
	String title;

	@Column(nullable = false)
	@Lob
	String content;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	Date date;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	BooleanState state;

	@ManyToOne
	@JsonManagedReference
	Users user;

	@ManyToOne
	@JsonIgnore
	Team team;

	@OneToMany(mappedBy = "referenceData")
	@JsonManagedReference
	List<ReferenceFileAndImg> fileList = new ArrayList<ReferenceFileAndImg>();
}
