package ljy.book.admin.professor.requestDTO;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;

import ljy.book.admin.entity.enums.BooleanState;
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
@Alias("PlanByUserDTO")
public class PlanByUserDTO {

	long seq;

	@NotNull(message = "태그를 입력해주세요")
	String tag;

	@NotNull(message = "일정 내용을 입력해주세요")
	String content;

	@NotNull(message = "일정 시작일을 입력해주세요")
	String start;

	@NotNull(message = "일정 종료일을 입력해주세요")
	String end;

	BooleanState teamPlan;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	byte progress;
}
