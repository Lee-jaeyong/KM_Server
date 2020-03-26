package ljy.book.admin.request;

import javax.validation.constraints.NotNull;

import ljy.book.admin.entity.enums.BooleanState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KM_reportVO {

	long classIdx;

	Long seq;

	@NotNull(message = "과제명을 입력해주세요.")
	String name;

	@NotNull(message = "과제 시작일을 입력해주세요.")
	String startDate;

	@NotNull(message = "과제 마감일을 입력해주세요.")
	String endDate;

	String content = "";

	long hit;

	@NotNull(message = "마감일 이후 제출 가능 여부를 입력해주세요.")
	BooleanState submitOverDue_state;

	@NotNull(message = "학생 제출 과제 공개 상태를 입력해주세요.")
	BooleanState showOtherReportOfStu_state;

	String fileList;
	String imgList;
}
