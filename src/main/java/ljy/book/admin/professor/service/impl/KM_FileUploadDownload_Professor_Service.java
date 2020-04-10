package ljy.book.admin.professor.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ljy.book.admin.common.exception.FileDownloadException;
import ljy.book.admin.common.exception.FileUploadException;
import ljy.book.admin.common.object.CustomFileUpload;
import ljy.book.admin.entity.enums.FileType;
import ljy.book.admin.service.KM_FileUploadDownloadServiceList;

@Service
public class KM_FileUploadDownload_Professor_Service implements KM_FileUploadDownloadServiceList {

	@Autowired
	KM_Class_Professor_Service km_classService;

	@Autowired
	KM_Report_Professor_Service km_reportService;

	private final Path fileLocation;

	@Autowired
	public KM_FileUploadDownload_Professor_Service(CustomFileUpload prop) {
		this.fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();

//		File directory = new File(prop.getUploadDir() + "/abc");
//		if (!directory.exists()) {
//			directory.mkdir();
//		}

		try {
			Files.createDirectories(this.fileLocation);
		} catch (Exception e) {
			throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
		}
	}

	public String storeFileReport(MultipartFile file, String uploadType, String fileType, long idx) {
		String originFileName = file.getOriginalFilename();
		this.customFileSave(file, uploadType, idx);
		FileType _fileType = FileType.valueOf(fileType.toUpperCase());
		km_reportService.uploadFile(originFileName, _fileType, idx);
		return originFileName;
	}

	public String storeFile(MultipartFile file, String uploadType, long idx) {
		String originFileName = file.getOriginalFilename();
		this.customFileSave(file, uploadType, idx);
		if (uploadType.equals("classInfoExcel")) {
			km_classService.uploadFile(originFileName, idx);
		}
		return originFileName;
	}

	public File a() throws FileNotFoundException {
		String resultfilePath = "\\professor\\downloadList\\classInfoExcel\\1.png";
		File file = new File(this.fileLocation.toString() + resultfilePath);
		if(file.exists()) {
			System.out.println("fdsjkhfsdjkhfjskd");
		}
		return file;
	}
	
	public Resource loadFileAsResource(String type, String idx, String fileName) throws Throwable {
		try {
			String resultfilePath = "";
			if (type.equals("reportIMG"))
				resultfilePath += "downloadList/report/reportImg/" + idx + "/";
			else if (type.equals("reportFile"))
				resultfilePath += "downloadList/report/reportFile/" + idx + "/";
			resultfilePath += fileName;
			Path filePath = this.fileLocation.resolve(resultfilePath).normalize();
			Resource resource = new UrlResource(filePath.toUri());

//			CustomZipFileConverter zipFileConverter = new CustomZipFileConverter();
//			
//			CustomZipFileConverter.compress("C:\\Users\\LJY\\Desktop\\DFDFD", "C:\\Users\\LJY\\Desktop\\DFDFD.zip");

			if (resource.exists()) {
				return resource;
			} else {
				throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
			}
		} catch (MalformedURLException e) {
			throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
		}
	}

	public boolean deleteFile(long idx, String deleteType, String fileName, String fileForm) {
		this.customFileDelete(idx, deleteType, fileName, fileForm);
		if (deleteType.equals("reportRelatedFiles")) {
			km_reportService.deleteFile(idx, fileName + "." + fileForm);
		}
		return true;
	}

	public boolean deleteFileAll(long idx, String deleteType) throws Exception {
		return this.customFileDeleteAll(idx, deleteType);
	}

	public  boolean customFileDelete(long idx, String deleteType, String fileName, String fileForm) {
		String resultfilePath = "\\professor\\downloadList\\" + deleteType + "\\" + idx + "\\" + fileName + "." + fileForm;
		File deleteFile = new File(this.fileLocation.toString() + resultfilePath);
		if (deleteFile.exists()) {
			deleteFile.delete();
			String directoryPath = "\\professor\\downloadList\\" + deleteType + "\\" + idx;
			File directory = new File(this.fileLocation.toString() + directoryPath);
			if (directory.isDirectory()) {
				directory.delete();
			}
		}
		return true;
	}

	private boolean customFileDeleteAll(long idx, String deleteType) throws Exception {
		String resultfilePath = "\\professor\\downloadList\\" + deleteType + "\\" + idx;
		File deleteFile = new File(this.fileLocation.toString() + resultfilePath);
		if (deleteFile.exists()) {
			if (deleteFile.isDirectory()) {
				File[] folder_list = deleteFile.listFiles();
				for (int j = 0; j < folder_list.length; j++) {
					folder_list[j].delete();
				}
				deleteFile.delete();
				if (deleteType.equals("classInfoExcel"))
					km_classService.deletePlannerDoc(idx);
			}
		}
		return true;
	}

	private boolean customFileSave(MultipartFile file, String uploadType, long idx) {
		if (!uploadType.equals("classInfoExcel") && !uploadType.equals("reportRelatedFiles")) {
			return false;
		}
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path targetLocation = null;
		File directory = new File(this.fileLocation.toString() + "\\professor\\downloadList\\" + uploadType + "\\" + idx);
		if (!directory.exists()) {
			directory.mkdir();
		}
		targetLocation = this.fileLocation
			.resolve(this.fileLocation + "/professor/downloadList/" + uploadType + "/" + idx + "\\" + fileName);
		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
