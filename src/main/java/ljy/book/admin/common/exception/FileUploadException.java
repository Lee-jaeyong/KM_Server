package ljy.book.admin.common.exception;

@SuppressWarnings("serial")
public class FileUploadException extends RuntimeException {
	public FileUploadException(String message) {
		super(message);
	}

	public FileUploadException(String message, Throwable cause) {
		super(message, cause);
	}
}
