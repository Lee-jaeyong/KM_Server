package ljy.book.admin.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ljy.book.admin.entity.enums.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FreeBoard {

	@Id
	@GeneratedValue
	long seq;

	@Column(nullable = false)
	String title;

	@Column(nullable = false)
	String content;

	@Column(nullable = false)
	String date;

	@Enumerated(EnumType.STRING)
	BoardType type;

	@ManyToOne
	Users user;

	@ManyToOne
	Team team;

	@OneToMany(mappedBy = "freeBoard")
	List<BoardFileAndImg> fileList = new ArrayList<BoardFileAndImg>();
}