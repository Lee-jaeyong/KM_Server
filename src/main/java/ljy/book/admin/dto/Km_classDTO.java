package ljy.book.admin.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Km_classDTO {

	@NotNull
	Long subject_seq;
	Long classIdx;
	String classCode;
	@NotNull(message = "수업명을 입력해주세요")
	String className;
	@NotNull(message = "수업 시작일을 선택해주세요")
	String startClassDate;
	@NotNull(message = "수업 종료일을 선택해주세요")
	String endClassDate;
	String classContent;
	@NotNull(message = "수업 타입을 지정해주세요")
	String classType;
}
