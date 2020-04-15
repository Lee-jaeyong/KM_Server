package ljy.book.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ljy.book.admin.entity.enums.FileType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PlanFileAndImg {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	String name;

	@Column(nullable = false)
	String date;

	@Enumerated(EnumType.STRING)
	FileType type;

	@ManyToOne
	PlanByUser plan;
}
