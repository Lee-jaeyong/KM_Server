package ljy.book.admin.professor.requestDTO;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("FreeBoardDTO")
public class FreeBoardDTO {
	long seq;
	@NotNull(message = "제목을 입력해주세요")
	String title;
	@NotNull(message = "내용을 입력해주세요")
	String content;
}
