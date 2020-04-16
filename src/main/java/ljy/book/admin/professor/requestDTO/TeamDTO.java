package ljy.book.admin.professor.requestDTO;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Alias("TeamDTO")
public class TeamDTO {
	long seq;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	String code;

	@NotNull(message = "팀명을 입력해주세요")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String name;
	@NotNull(message = "팀 시작일을 입력해주세요")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String startDate;
	@NotNull(message = "팀 마감일을 입력해주세요")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String endDate;
	@NotNull(message = "팀 최종 목표를 입력해주세요")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String description;

	byte progress;
}
