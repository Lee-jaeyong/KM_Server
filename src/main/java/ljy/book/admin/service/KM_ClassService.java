package ljy.book.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

@Service
public class KM_ClassService {

	@Autowired
	KM_ClassAPI km_classAPI;

	@Autowired
	KM_FileUploadDownloadService fileUploadService;

	@Autowired
	KM_SubjectService km_subjectService;

	@Autowired
	ModelMapper modelMapper;

//	public List<Km_classProjection> findByUserId(KM_user km_user) {
//		return null;
//		// return km_classAPI.findByKmUser_id(km_user.getId());
//	}

	public Page<KM_class> getClassListPage(Pageable pageable) {
		return km_classAPI.findByName("ㅁㅁㅁ", pageable);
	}

	public List<KM_classVO> getClassList() {
		List<KM_class> list = km_classAPI.findAll();
		List<KM_classVO> resultList = new ArrayList<KM_classVO>();
		for (KM_class listData : list) {
			resultList.add(modelMapper.map(listData, KM_classVO.class));
		}
		return resultList;
	}

	public KM_classVO getClassInfo(long idx) {
		return modelMapper.map(km_classAPI.findById(idx).get(), KM_classVO.class);
	}

	public KM_class checkByKm_user(KM_user km_user) {
		return km_classAPI.findBykmUser_Id(km_user.getId()).get();
	}

	public boolean uploadFile(String fileName, long idx) {
		km_classAPI.plannerDocFileUpload(fileName, idx);
		return true;
	}

	public KM_class save(KM_class km_class) {
		KM_user km_user = new KM_user();
		km_user.setSeq(1L);
		km_user.addKmClass(km_class);
		return km_classAPI.save(km_class);
	}

	public KM_class update(KM_class km_class) {
		km_classAPI.updateKm_class(km_class.getName(), km_class.getStartDate(), km_class.getEndDate(),
				km_class.getContent(), km_class.getType(), km_class.getReplyPermit_state(), km_class.getSelectMenu(),
				km_class.getUse_state(), km_class.getSeq());
		return km_classAPI.findById(km_class.getSeq()).get();
	}

	public boolean deletePlannerDoc(long idx) {
		km_classAPI.deletePlannerDocFile(idx);
		return true;
	}

//	public Km_classDTO save(Km_classDTO km_classDTO, HttpSession session) {
//		KM_class km_class = modelMapper.map(km_classDTO, KM_class.class);
//		KM_subject km_subject = new KM_subject();
//		km_subject.setSeq(km_classDTO.getSubject_seq());
//		//km_subject.addKm_class(km_class);
//		km_subject = km_subjectService.findBySeq(km_subject);
//		KM_user km_user = new KM_user();
//		//km_user.setId(session.getAttribute("id").toString());
//		km_user = km_userService.findById(km_user);
//		//km_user.addKm_class(km_class);
//		return modelMapper.map(km_classAPI.save(km_class), Km_classDTO.class);
//	}

//	public KM_class findByClassIdx(KM_class km_class) {
//		// return km_classAPI.findByClassIdx(km_class.getClassIdx());
//		return null;
//	}

//	public List<Km_classDTO> getKm_classByUserId(Km_user km_user) {
//		List<Km_classDTO> result = new ArrayList<>();
//		List<Km_class> km_class = km_classAPI.findAllByUser_Id(km_user.getId());
//		for (Km_class c : km_class)
//			result.add(new Km_classDTO(c));
//		return result;
//	}

	public HashMap<String, String> addKm_classByUser(KM_class km_class, KM_user km_user, KM_subject km_subject) {
//		HashMap<String, String> result = new HashMap<>(); // 결과값을 담을 HashMap
//		Km_subject subject = km_subjectService.findOne(km_subject); // 요청한 학과의 존재 유무를 확인
//		Km_user user = km_userService.findByUserId(km_user); // 요청한 학생의 존재 유무를 확인
//		user.addKm_class(km_class);
//		km_classAPI.save(km_class);
//		subject.addKm_class(km_class);
//		km_class.insertClassCode(km_class.getClassIdx());
//		km_classAPI.save(km_class);
//		result.put("result", "성공적으로 등록되었습니다.");
//		return result;
		return null;
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
