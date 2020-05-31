package ljy_yjw.team_manage.system.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

	@Memo("ZIP파일 다운로드")
	public Resource zipFileDownload(List<HashMap<String, Object>> fileList, String zipFileName, String type, long idx)
		throws IOException {
		FileOutputStream zipFileOutputStream = new FileOutputStream(
			this.fileLocation.resolve(this.fileLocation + "/" + type + "/" + idx + "\\" + zipFileName + ".zip").toString());
		ZipOutputStream zipOutputStream = new ZipOutputStream(zipFileOutputStream);

		fileList.forEach(c -> {
			ZipEntry zipEntry = new ZipEntry(c.get("fileName").toString());
			try {
				zipOutputStream.putNextEntry(zipEntry);
				zipOutputStream.write((byte[]) c.get("fileByte"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		zipOutputStream.close();
		zipFileOutputStream.close();
		Path filePath = this.fileLocation.resolve(this.fileLocation + "//" + type + "//" + idx + "//" + zipFileName + ".zip")
			.normalize();
		Resource resource = new UrlResource(filePath.toUri());
		return resource;
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

	public boolean deleteFile(long idx, String deleteType, String fileName) throws IOException {
		return this.customFileDelete(idx, deleteType, fileName);
	}

	public boolean customFileDelete(long seq, String deleteType, String fileName) throws IOException {
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
