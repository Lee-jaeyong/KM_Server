package ljy.book.admin.professor.requestDTO;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamDTO {
	long seq;
	@NotNull(message = "팀명을 입력해주세요")
	String name;
	@NotNull(message = "팀 시작일을 입력해주세요")
	String startDate;
	@NotNull(message = "팀 마감일을 입력해주세요")
	String endDate;
	@NotNull(message = "팀 최종 목표를 입력해주세요")
	String description;
}
