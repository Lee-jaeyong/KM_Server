package ljy.book.admin.dto;

import ljy.book.admin.entity.KM_subject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Km_subjectDTO {
	Long seq;
	String subjectNM;

	public Km_subjectDTO(KM_subject subject) {
		this.seq = subject.getSeq();
	//	this.subjectNM = subject.getSubjectNM();
	}
}
