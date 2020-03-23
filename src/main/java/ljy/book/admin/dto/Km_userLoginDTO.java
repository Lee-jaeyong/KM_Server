package ljy.book.admin.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Km_userLoginDTO {
	@NotNull(message = "아이디를 입력해주세요.")
	String id;

	@NotNull(message = "비밀번호를 입력해주세요.")
	String pass;
}
