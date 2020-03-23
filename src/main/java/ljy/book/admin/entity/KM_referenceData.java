package ljy.book.admin.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class KM_referenceData {

	@Id
	@GeneratedValue
	long seq;

	@Lob
	String title;

	@Lob
	String content;

	@Temporal(TemporalType.DATE)
	Date date;

	long hit;

	boolean show_state;

	//KM_class km_class;
	//List<KM_fileAndImgOfReferenceData> kmFileAndImgOfReferenceData = new ArrayList<KM_fileAndImgOfReferenceData>();
}
