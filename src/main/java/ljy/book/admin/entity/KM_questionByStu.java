package ljy.book.admin.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class KM_questionByStu {

	@Id
	@GeneratedValue
	long seq;

	@Lob
	String title;

	@Lob
	String content;

	@Temporal(TemporalType.DATE)
	Date date;

//	Km_user kmUser;
//	KM_answerByProfessor kmAnswerByProfessor;
//	List<KM_fileAndImgOfQuestionByStu> kmFileAndImgOfQuestionByStu;
}
