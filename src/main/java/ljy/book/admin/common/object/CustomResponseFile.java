package ljy.book.admin.common.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponseFile {
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;

	public CustomResponseFile(String fileName, String fileDownloadUri, String fileType, long size) {
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}
}
