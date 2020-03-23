package ljy.book.admin.common.object;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "file")
public class CustomFileUpload {
	private String uploadDir;
}
