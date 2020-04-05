package ljy.book.admin.request;

import java.util.Set;

import javax.validation.constraints.NotNull;

import ljy.book.admin.entity.enums.ClassType;
import ljy.book.admin.entity.enums.SelectClassMenu;
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
	String content;
	String plannerDocName;
	@NotNull(message = "수업 타입을 입력해주세요.")
	ClassType type;
	@NotNull(message = "선택 메뉴를 입력해주세요.")
	Set<SelectClassMenu> selectMenu;
}
