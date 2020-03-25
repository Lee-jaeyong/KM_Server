package ljy.book.admin.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ljy.book.admin.common.exception.FileDownloadException;
import ljy.book.admin.common.exception.FileUploadException;
import ljy.book.admin.common.object.CustomFileUpload;
import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.entity.enums.FileType;
import ljy.book.admin.repository.projection.Km_ReportFileProjection;
import ljy.book.admin.repository.projection.Km_ReportImgProjection;

@Service
public class KM_FileUploadDownloadService {

//	@Autowired
//	KM_ReportImgAPI km_reportImgAPI;
//
//	@Autowired
//	KM_ReportFileAPI km_reportFileAPI;

	@Autowired
	KM_ReportFileAndImgService km_reportFileAndImgService;

	@Autowired
	KM_ClassService km_classService;

	@Autowired
	KM_ReportService km_reportService;

	private final Path fileLocation;

	@Autowired
	public KM_FileUploadDownloadService(CustomFileUpload prop) {
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

	public String save_ReportImgOrFile(MultipartFile file, String uploadType, KM_Report km_report) {
		// Optional<KM_Report> check_Report =
		// km_reportService.checkByReportIdx(km_report);
//		Km_ReportFile km_reportFile;
//		Km_ReportImg km_reportImg;
//		if (uploadType.equals("addReport_Img")) {
//			km_reportImg = new Km_ReportImg();
//			km_reportImg.setReportImg(file.getOriginalFilename());
//			//check_Report.get().addKm_ReportImg(km_reportImg);
//			km_reportImgAPI.save(km_reportImg);
//		} else {
//			km_reportFile = new Km_ReportFile();
//			km_reportFile.setReportFile(file.getOriginalFilename());
//			//check_Report.get().addKm_ReportFile(km_reportFile);
//			km_reportFileAPI.save(km_reportFile);
//		}
		// return storeFile(file, uploadType, km_report.getReportIdx());
		return null;
	}

	public List<Km_ReportImgProjection> findByReportIdxAsImg(KM_Report km_report) {
		// return km_reportImgAPI.findByKmReport_ReportIdx(km_report.getReportIdx());
		return null;
	}

	public List<Km_ReportFileProjection> findByReportIdxAsFile(KM_Report km_report) {
//		return km_reportFileAPI.findByKmReport_ReportIdx(km_report.getReportIdx());
		return null;
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
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		Path targetLocation = null;
//		if (uploadType.equals("addClass")) {
//			km_classService.fileUpload_Km_class(classIdx, file.getOriginalFilename());
//			File directory = new File(this.fileLocation.toString() + "\\downloadList\\classInfoExcel\\" + classIdx);
//			if (!directory.exists())
//				directory.mkdir();
//			targetLocation = this.fileLocation
//					.resolve(this.fileLocation + "/downloadList/classInfoExcel/" + classIdx + "\\" + fileName);
//		} else if (uploadType.equals("addReport_Img")) {
//
//		} else if (uploadType.equals("addReport_File")) {
//			KM_Report km_report = new KM_Report();
//			// km_report.setReportIdx(classIdx);
//
//			// Km_ReportFile km_reportFile = new Km_ReportFile();
////			km_reportFile.setReportFile(file.getOriginalFilename());
////			//km_report.addKm_ReportFile(km_reportFile);
////
////			km_reportFileAndImgService.save_File(km_reportFile);
//
//			File directory = new File(this.fileLocation.toString() + "\\downloadList\\report\\reportFile\\" + classIdx);
//			if (!directory.exists())
//				directory.mkdir();
//			targetLocation = this.fileLocation
//					.resolve(this.fileLocation + "/downloadList/report/reportFile/" + classIdx + "\\" + fileName);
//		}
//		return fileName;
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

	public boolean deleteFile(long idx, String deleteType) throws Exception {
		return this.customFileDeleteAll(idx, deleteType);
//		if (type.equals("reportIMG"))
//			resultfilePath += "downloadList/report/reportImg/" + idx + "/";
//		else if (type.equals("reportFile"))
//			resultfilePath += "downloadList/report/reportFile/" + idx + "/";
//		resultfilePath += fileName;
//		Path filePath = this.fileLocation.resolve(resultfilePath).normalize();
//		Resource resource = new UrlResource(filePath.toUri());
//		File deleteFile = resource.getFile();
//		if (deleteFile.exists()) {
//			if (deleteFile.delete()) {
//				if (type.equals("reportIMG")) {
//					km_reportImgAPI.deleteByReportImgIdx(Long.parseLong(fileIdx));
//				} else {
//					km_reportFileAPI.deleteByReportFileIdx(Long.parseLong(fileIdx));
//				}
//				result.put("result", true);
//			} else
//				result.put("result", false);
//		} else {
//			result.put("result", false);
//		}
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
		km_classService.fileUpload_Km_class(idx, file.getOriginalFilename());
		File directory = new File(
				this.fileLocation.toString() + "\\professor\\downloadList\\" + uploadType + "\\" + idx);
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
