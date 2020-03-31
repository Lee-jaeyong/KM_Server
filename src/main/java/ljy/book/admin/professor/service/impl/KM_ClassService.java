package ljy.book.admin.professor.service.impl;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ljy.book.admin.common.object.CustomCodeCreator;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.customRepository.mybaits.Km_classDAO;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.jpaAPI.KM_ClassAPI;
import ljy.book.admin.request.KM_classVO;
import ljy.book.admin.security.KM_UserService;
import ljy.book.admin.service.KM_classServiceList;

@Service
@Transactional
public class KM_ClassService implements KM_classServiceList {

	@Autowired
	KM_ClassAPI km_classAPI;

	@Autowired
	Km_classDAO customKm_classAPI;

	@Autowired
	KM_UserService km_userService;

	@Autowired
	KM_FileUploadDownloadService fileUploadService;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CustomCodeCreator customCodeCreator;

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
	 * 교수 권한 메소드
	 */

	@Memo("강의 계획서를 업로드하는 메소드")
	public boolean uploadFile(String fileName, long idx) {
		km_classAPI.plannerDocFileUpload(fileName, idx);
		return true;
	}

	@Memo("수업을 등록하는 메소드")
	public KM_class save(KM_class km_class, String id) {
		KM_user user = km_userService.findByUserId(id);
		user.addKmClass(km_class);
		KM_class result = km_classAPI.save(km_class);
		result.setClassCode(customCodeCreator.createCode(result.getSeq().toString()));
		return result;
	}

	@Memo("수업을 수정하는 메소드")
	public KM_class update(KM_class km_class, String id) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", km_class.getName());
		map.put("startDate", km_class.getStartDate());
		map.put("endDate", km_class.getEndDate());
		map.put("content", km_class.getContent());
		map.put("type", km_class.getType().toString());
		map.put("replyPermit_state", km_class.getReplyPermit_state().toString());
		map.put("selectMenu", km_class.getSelectMenu());
		map.put("use_state", km_class.getUse_state().toString());
		map.put("idx", km_class.getSeq().toString());
		map.put("userId", id);

		customKm_classAPI.update(map);

		return km_classAPI.findById(km_class.getSeq()).get();
	}

	@Memo("강의 계획서를 삭제하는 메소드")
	public boolean deletePlannerDoc(long idx) {
		km_classAPI.deletePlannerDocFile(idx);
		return true;
	}

	@Memo("수업을 삭제하는 메소드")
	public HashMap<String, String> deleteKm_classByUser(KM_class km_class) {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			// km_classAPI.deleteById(km_class.getClassIdx());
			result.put("result", "수업 삭제 완료");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "수업 삭제 실패");
		}
		return result;
	}
}
