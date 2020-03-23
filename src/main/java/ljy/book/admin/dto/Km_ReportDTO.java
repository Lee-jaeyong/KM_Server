package ljy.book.admin.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Km_ReportDTO {
	Long classIdx;
	Long reportIdx;
	@NotNull(message = "과제명을 입력해주세요.")
	String reportTitle;
	@NotNull(message = "과제 내용을 입력해주세요.")
	String reportContent;
	int reportHit;
	@NotNull(message = "과제 시작일을 선택해주세요.")
	String reportStartDate;
	@NotNull(message = "과제 마감일을 선택해주세요.")
	String reportEndDate;
}
