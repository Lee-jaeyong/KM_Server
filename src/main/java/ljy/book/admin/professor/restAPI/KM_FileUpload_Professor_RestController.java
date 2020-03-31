package ljy.book.admin.professor.restAPI;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.professor.service.impl.KM_Class_Professor_Service;
import ljy.book.admin.professor.service.impl.KM_FileUploadDownload_Professor_Service;
import ljy.book.admin.professor.service.impl.KM_Report_Professor_Service;
import ljy.book.admin.security.CurrentKm_User;

@RestController
@RequestMapping(value = "api/professor/uploadFile/{idx}")
public class KM_FileUpload_Professor_RestController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	KM_Report_Professor_Service km_reportService;

	@Autowired
	KM_Class_Professor_Service km_classService;

	@Autowired
	private KM_FileUploadDownload_Professor_Service fileUploadService;

	@PostMapping("/{uploadType}/{fileType}")
	@Memo("과제 관련 이미지 및 파일을 업로드하는 메소드")
	public ResponseEntity<?> uploadFile(@PathVariable long idx, @PathVariable String uploadType, @PathVariable String fileType,
		MultipartFile[] file, @CurrentKm_User KM_user km_user) {
		if (!km_reportService.checkBySeqAndUserId(idx, km_user.getId())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		StringBuilder result = new StringBuilder();
		for (int i = 0; i < file.length; i++) {
			result.append(file[i].getOriginalFilename() + ",");
			fileUploadService.storeFileReport(file[i], uploadType, fileType, idx);
		}

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("fileList", result.toString());
		HashMap<String, Object> _links = new HashMap<String, Object>();
		_links.put("profile", new Link("/docs/index.html").withRel("profile"));
		resultMap.put("_links", _links);
		return ResponseEntity.status(HttpStatus.OK).body(resultMap);
	}

	@PostMapping("/{uploadType}")
	@Memo("강의 계획서 업로드 하는 메소드")
	public ResponseEntity<?> uploadFile(@PathVariable long idx, @PathVariable String uploadType, MultipartFile file,
		@CurrentKm_User KM_user km_user) {
		if (!km_classService.checkByKm_user(idx, km_user.getId())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("file", fileUploadService.storeFile(file, uploadType, idx));
		HashMap<String, Object> _links = new HashMap<String, Object>();
		_links.put("profile", new Link("/docs/index.html").withRel("profile"));
		resultMap.put("_links", _links);
		return ResponseEntity.status(HttpStatus.OK).body(resultMap);
	}

	@DeleteMapping("/{deleteType}/{fileName}/{fileForm}")
	@Memo("선택 파일을 삭제하는 메소드")
	public boolean deleteFile(@PathVariable long idx, @PathVariable String deleteType, @PathVariable String fileName,
		@PathVariable String fileForm) {
		return fileUploadService.deleteFile(idx, deleteType, fileName, fileForm);
	}

	@DeleteMapping(value = "/{deleteType}")
	@Memo("해당 타입의 파일 리스트를 모두 삭제하는 메소드")
	public boolean deleteFileAll(@PathVariable long idx, @PathVariable String deleteType) throws Exception {
		return fileUploadService.deleteFileAll(idx, deleteType);
	}
}
