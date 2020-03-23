package ljy.book.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy.book.admin.entity.KM_subject;
import ljy.book.admin.jpaAPI.KM_subjectAPI;

@Service
public class KM_SubjectService {

	@Autowired
	KM_subjectAPI km_subjectAPI;

	public KM_subject findBySeq(KM_subject km_subject) {
	//	return km_subjectAPI.findBySeq(km_subject.getSeq());
		return null;
	}
}
