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

import ljy.book.admin.entity.enums.FileType;
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
public class NoticeFileAndImg {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	String name;

	@Column(nullable = false)
	String date;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	FileType type;

	@ManyToOne
	@JsonBackReference
	Notice notice;
}
