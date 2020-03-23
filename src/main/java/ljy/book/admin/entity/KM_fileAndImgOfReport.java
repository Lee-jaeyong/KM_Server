package ljy.book.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ljy.book.admin.entity.enums.FileType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class KM_fileAndImgOfReport {

	@Id
	@GeneratedValue
	Long seq;

	String fileName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	FileType type;
}
