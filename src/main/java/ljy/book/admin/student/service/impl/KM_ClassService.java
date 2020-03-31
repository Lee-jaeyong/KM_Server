package ljy.book.admin.student.service.impl;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.KM_signUpClassForStu;
import ljy.book.admin.jpaAPI.KM_ClassAPI;
import ljy.book.admin.jpaAPI.KM_signUpClassForStuAPI;
import ljy.book.admin.request.KM_classVO;
import ljy.book.admin.service.KM_classServiceList;

@Service
@Transactional
public class KM_ClassService implements KM_classServiceList {

	@Autowired
	KM_signUpClassForStuAPI km_signUpClassForStuAPI;
	
	@Autowired
	KM_ClassAPI km_classAPI;

	@Autowired
	ModelMapper modelMapper;

	@Override
	@CacheEvict(value = "getClassListPage")
	public Page<KM_class> getClassListPage(String id, Pageable pageable) {
		return km_classAPI.findByKmUser_Id(id, pageable);
	}

	@Override
	public KM_classVO getClassInfo(long idx, String id) {
		return modelMapper.map(km_classAPI.findBySeqAndKmUser_Id(idx, id), KM_classVO.class);
	}

	@Override
	public boolean checkByKm_user(long idx, String id) {
		return km_classAPI.findBySeqAndKmUser_Id(idx, id) == null ? false : true;
	}

	/*
	 * 학생 권한 메소드
	 */

	@Memo("학생 수업 승인 요청 메소드")
	public void signUpClassForStu(long classIdx, String id) {
		KM_signUpClassForStu km_signUpClassForStu = new KM_signUpClassForStu();
		KM_class km_class = new KM_class();
		km_class.setSeq(classIdx);
		km_class.addKmSignUpClassForStu(km_signUpClassForStu);
		km_signUpClassForStuAPI.save(km_signUpClassForStu);
	}
}
