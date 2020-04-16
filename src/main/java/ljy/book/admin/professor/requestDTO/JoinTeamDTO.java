package ljy.book.admin.professor.requestDTO;

import ljy.book.admin.entity.enums.BooleanState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinTeamDTO {
	long seq;
	String date;
	BooleanState state;
}
