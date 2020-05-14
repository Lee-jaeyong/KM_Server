package ljy_yjw.team_manage.system.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.util.CustomFileUpload;

//CustomZipFileConverter zipFileConverter = new CustomZipFileConverter();
//
//CustomZipFileConverter.compress("C:\\Users\\LJY\\Desktop\\DFDFD", "C:\\Users\\LJY\\Desktop\\DFDFD.zip");

@Service
public class CommonFileUpload {
	private final Path fileLocation;

	// 초기 파일 루트 디렉터리 생성
	@Autowired
	public CommonFileUpload(CustomFileUpload prop) {
		this.fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Resource loadFileAsResource(long seq, String type, String fileName) {
		try {
			Path filePath = this.fileLocation.resolve(this.fileLocation + "//" + type + "//" + seq + "//" + fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public String storeFile(MultipartFile file, String uploadType, long idx) {
		return this.customFileSave(file, uploadType, idx);
	}

	public File a() throws FileNotFoundException {
		String resultfilePath = "\\professor\\downloadList\\classInfoExcel\\1.png";
		File file = new File(this.fileLocation.toString() + resultfilePath);
		if (file.exists()) {
			System.out.println("fdsjkhfsdjkhfjskd");
		}
		return file;
	}

	public boolean deleteFile(long idx, String deleteType, String fileName) {
		return this.customFileDelete(idx, deleteType, fileName);
	}

	public boolean customFileDelete(long seq, String deleteType, String fileName) {
		String resultfilePath = "\\" + deleteType + "\\" + seq + "\\" + fileName;
		File deleteFile = new File(this.fileLocation.toString() + resultfilePath);
		if (deleteFile.exists()) {
			deleteFile.delete();
			String directoryPath = "\\" + deleteType + "\\" + seq;
			File directory = new File(this.fileLocation.toString() + directoryPath);
			if (directory.isDirectory()) {
				File[] folder_list = directory.listFiles();
				if (folder_list.length == 0)
					directory.delete();
			}
		}
		return true;
	}

	@Memo("update Type은 폴더 경로 , idx는 시퀀스")
	private String customFileSave(MultipartFile file, String uploadType, long idx) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path targetLocation = null;
		File directory = new File(this.fileLocation.toString() + "\\" + uploadType + "\\" + idx);
		if (!directory.exists()) {
			directory.mkdir();
		}
		try {
			String saveName = this.uudUploadFile(fileName, file.getBytes());
			targetLocation = this.fileLocation.resolve(this.fileLocation + "/" + uploadType + "/" + idx + "\\" + saveName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return saveName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String uudUploadFile(String originName, byte[] fileDta) {
		UUID uuid = UUID.randomUUID();
		String saveName = uuid.toString() + "_" + originName;
		return saveName;
	}
}
