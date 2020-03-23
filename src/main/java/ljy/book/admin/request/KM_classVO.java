package ljy.book.admin.request;

import javax.validation.constraints.NotNull;

import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.SaveState;
import ljy.book.admin.entity.enums.ClassType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KM_classVO {
	Long seq;

	@NotNull(message = "수업명을 입력해주세요.")
	String name;
	@NotNull(message = "수업 시작일을 입력해주세요.")
	String startDate;
	@NotNull(message = "수업 종료일을 입력해주세요.")
	String endDate;
	String content;
	String plannerDocName;
	@NotNull(message = "수업 타입을 입력해주세요.")
	ClassType type;
	@NotNull(message = "댓글 허용 여부를 입력해주세요.")
	BooleanState replyPermit_state;
	@NotNull(message = "선택 메뉴를 입력해주세요.")
	String selectMenu;
	@NotNull(message = "수업 사용 여부를 입력해주세요.")
	BooleanState use_state;
	SaveState saveState;
}
