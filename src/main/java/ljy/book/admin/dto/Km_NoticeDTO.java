package ljy.book.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Km_NoticeDTO {
	Long noticeIdx;
	String noticeTitle;
	String noticeContent;
	int noticeHit;
	String noticeDate;

}
