package ljy.book.admin.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import ljy.book.admin.custom.anotation.Memo;

public interface KM_FileUploadDownloadServiceList {

	@Memo("과제 파일을 저장하는 메소드")
	public String storeFileReport(MultipartFile file, String uploadType, String fileType, long idx);

	@Memo("강의 계획서 등 파일을 저장하는 메소드")
	public String storeFile(MultipartFile file, String uploadType, long idx);

	@Memo("파일을 읽어들이는 메소드")
	public Resource loadFileAsResource(String type, String idx, String fileName) throws Throwable;

	@Memo("단건 파일을 삭제하는 메소드")
	public boolean deleteFile(long idx, String deleteType, String fileName, String fileForm);

	@Memo("해당 타입의 전제 파일을 삭제하는 메소드")
	public boolean deleteFileAll(long idx, String deleteType) throws Exception;
}
