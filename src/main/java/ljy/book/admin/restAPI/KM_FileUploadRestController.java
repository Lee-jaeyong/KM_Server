package ljy.book.admin.restAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ljy.book.admin.service.KM_FileUploadDownloadService;

@RestController
public class KM_FileUploadRestController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KM_FileUploadDownloadService fileUploadService;

	@PostMapping("/uploadFile/{idx}/{uploadType}")
	public String uploadFile(@PathVariable long idx, @PathVariable String uploadType, MultipartFile file) {
		return fileUploadService.storeFile(file, uploadType, idx);
	}

	@DeleteMapping(value = "/uploadFile/{idx}/{deleteType}")
	public boolean deleteFileAll(@PathVariable long idx, @PathVariable String deleteType) throws Exception {
		return fileUploadService.deleteFile(idx, deleteType);
	}

//	@PostMapping("/uploadFile")
//	public CustomResponseFile uploadFile(MultipartFile file, HttpServletRequest request) {
//		String fileName = service.storeFile(file, request, -1);
//
//		String fileDownloadUri = "";
//
//		if (request.getParameter("type").equals("addClass")) {
//			fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//					.path("/downloadFile/" + request.getParameter("id")).path(fileName).toUriString();
//		}
//
//		return new CustomResponseFile(fileName, fileDownloadUri, file.getContentType(), file.getSize());
//	}

//	@PostMapping("/uploadMultipleFiles")
//	public List<CustomResponseFile> uploadMultipleFiles(MultipartFile[] files) {
//		return Arrays.asList(files).stream().map(file -> uploadFile(file, null)).collect(Collectors.toList());
//	}

//	@GetMapping("/downloadFile/{type}/{idx}/{fileName:.+}")
//	public ResponseEntity<Resource> downloadFile(@PathVariable String type, @PathVariable String idx,
//			@PathVariable String fileName, HttpServletRequest request) throws Throwable {
//		// Load file as Resource
//		Resource resource = service.loadFileAsResource(type, idx, fileName);
//
//		// Try to determine file's content type
//		String contentType = null;
//		try {
//			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//		} catch (IOException ex) {
//			logger.info("Could not determine file type.");
//		}
//
//		// Fallback to the default content type if type could not be determined
//		if (contentType == null) {
//			contentType = "application/octet-stream";
//		}
//
//		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//				.body(resource);
//	}
//
//	@DeleteMapping(value = "/downloadFile/{type}/{idx}/{fileIdx}/{fileName:.+}")
//	public HashMap<String, Object> deleteFile(@PathVariable String type, @PathVariable String idx,
//			@PathVariable String fileIdx, @PathVariable String fileName) throws Exception {
//		return service.deleteFile(type, idx, fileIdx, fileName);
//	}
}
