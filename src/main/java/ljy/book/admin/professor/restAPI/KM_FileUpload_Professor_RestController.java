package ljy.book.admin.professor.restAPI;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/professor/uploadFile/{idx}")
public class KM_FileUpload_Professor_RestController {

//	Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private KM_FileUploadDownload_Professor_Service fileUploadService;
//
//	@GetMapping(value = "/a")
//	public ResponseEntity<?> getimg() throws IOException {
//		InputStream in = new FileInputStream(fileUploadService.a());
//		ByteArrayOutputStream bao = new ByteArrayOutputStream();
//		int byteRead = 0;
//		byte[] buff = new byte[1024];
//		while((byteRead = in.read(buff)) > 0) {
//			bao.write(buff,0,byteRead);
//		}
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("byte", bao.toByteArray());
//		return ResponseEntity.ok(map);
//	}
//
//	@PostMapping("/{uploadType}/{fileType}")
//	@Memo("과제 관련 이미지 및 파일을 업로드하는 메소드")
//	public ResponseEntity<?> uploadFile(@PathVariable long idx, @PathVariable String uploadType, @PathVariable String fileType,
//		MultipartFile[] file, @CurrentKm_User KM_user km_user) {
//		if (!km_reportService.checkBySeqAndUserId(idx, km_user.getId())) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//
//		StringBuilder result = new StringBuilder();
//		for (int i = 0; i < file.length; i++) {
//			result.append(file[i].getOriginalFilename() + ",");
//			fileUploadService.storeFileReport(file[i], uploadType, fileType, idx);
//		}
//
//		HashMap<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("fileList", result.toString());
//		HashMap<String, Object> _links = new HashMap<String, Object>();
//		_links.put("profile", new Link("/docs/index.html").withRel("profile"));
//		resultMap.put("_links", _links);
//		return ResponseEntity.status(HttpStatus.OK).body(resultMap);
//	}
//
//	@PostMapping("/{uploadType}")
//	@Memo("강의 계획서 업로드 하는 메소드")
//	public ResponseEntity<?> uploadFile(@PathVariable long idx, @PathVariable String uploadType, MultipartFile file,
//		@CurrentKm_User KM_user km_user) {
//		if (!km_classService.checkByKm_user(idx, km_user.getId())) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		HashMap<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("file", fileUploadService.storeFile(file, uploadType, idx));
//		HashMap<String, Object> _links = new HashMap<String, Object>();
//		_links.put("profile", new Link("/docs/index.html").withRel("profile"));
//		resultMap.put("_links", _links);
//		return ResponseEntity.status(HttpStatus.OK).body(resultMap);
//	}
//
//	@DeleteMapping("/{deleteType}/{fileName}/{fileForm}")
//	@Memo("선택 파일을 삭제하는 메소드")
//	public boolean deleteFile(@PathVariable long idx, @PathVariable String deleteType, @PathVariable String fileName,
//		@PathVariable String fileForm) {
//		return fileUploadService.deleteFile(idx, deleteType, fileName, fileForm);
//	}
//
//	@DeleteMapping(value = "/{deleteType}")
//	@Memo("해당 타입의 파일 리스트를 모두 삭제하는 메소드")
//	public boolean deleteFileAll(@PathVariable long idx, @PathVariable String deleteType) throws Exception {
//		return fileUploadService.deleteFileAll(idx, deleteType);
//	}
}
