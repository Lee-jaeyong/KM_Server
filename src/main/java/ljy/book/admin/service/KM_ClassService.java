package ljy.book.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.h2.server.web.PageParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.KM_subject;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.jpaAPI.KM_ClassAPI;
import ljy.book.admin.request.KM_classVO;
import ljy.book.admin.security.KM_UserService;

@Service
public class KM_ClassService {

	@Autowired
	KM_ClassAPI km_classAPI;

	@Autowired
	KM_UserService km_userService;

	@Autowired
	KM_FileUploadDownloadService fileUploadService;

	@Autowired
	KM_SubjectService km_subjectService;

	@Autowired
	ModelMapper modelMapper;

	public Page<KM_class> getClassListPage(String id, Pageable pageable) {
		return km_classAPI.findByKmUser_Id(id, pageable);
	}

	public List<KM_classVO> getClassList() {
		List<KM_class> list = km_classAPI.findAll();
		List<KM_classVO> resultList = new ArrayList<KM_classVO>();
		for (KM_class listData : list) {
			resultList.add(modelMapper.map(listData, KM_classVO.class));
		}
		return resultList;
	}

	public KM_classVO getClassInfo(long idx, String id) {
		return modelMapper.map(km_classAPI.findBySeqAndKmUser_Id(idx, id), KM_classVO.class);
	}

	public KM_class checkByKm_user(KM_user km_user) {
		return km_classAPI.findBykmUser_Id(km_user.getId()).get();
	}

	public boolean uploadFile(String fileName, long idx) {
		km_classAPI.plannerDocFileUpload(fileName, idx);
		return true;
	}

	public KM_class save(KM_class km_class, String id) {
		KM_user user = km_userService.findByUserId(id);
		user.addKmClass(km_class);
		return km_classAPI.save(km_class);
	}

	public KM_class update(KM_class km_class, String id) {
		km_classAPI.updateKm_class(km_class.getName(), km_class.getStartDate(), km_class.getEndDate(), km_class.getContent(),
			km_class.getType().toString(), km_class.getReplyPermit_state().toString(), km_class.getSelectMenu(),
			km_class.getUse_state().toString(), km_class.getSeq(), id);
		return km_classAPI.findById(km_class.getSeq()).get();
	}

	public boolean deletePlannerDoc(long idx) {
		km_classAPI.deletePlannerDocFile(idx);
		return true;
	}

	public void fileUpload_Km_class(Long classIdx, String fileName) {
		// km_classAPI.fileUpload_Km_class(classIdx, fileName);
	}

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
