package ljy.book.admin.common.object;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ljy.book.admin.entity.enums.FileType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "file")
public class CustomFileUpload {
	private String uploadDir;

	@JsonIgnore
	public static boolean fileUploadFilter(String fileName, FileType type) {
		int fileFormCheck = fileName.indexOf(".");
		String fileForm = fileName.substring(fileFormCheck, fileName.length());
		if (type == FileType.FILE) {
			if (fileForm.equals(".jsp") || fileForm.equals(".asp") || fileForm.equals(".html") || fileForm.equals(".cer")
				|| fileForm.equals(".cdx") || fileForm.equals(".htm") || fileForm.equals(".php3") || fileForm.equals(".exe")
				|| fileForm.equals(".war"))
				return false;
			return true;
		} else {
			if (fileForm.equals(".jpg") || fileForm.equals(".png") || fileForm.equals(".jpeg"))
				return true;
			return false;
		}
	}
}
