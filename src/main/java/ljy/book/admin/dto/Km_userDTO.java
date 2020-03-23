package ljy.book.admin.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import ljy.book.admin.entity.enums.UserRule;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Km_userDTO {
	Long userSEQ;

	@NotNull(message = "아이디를 입력해주세요.")
	String id;

	@NotNull(message = "비밀번호를 입력해주세요.")
	String pass;

	UserRule rule;

	@Email
	String email;

	@Email
	String phone;

	@Email
	@Size(min = 1, max = 6)
	String birth;

	@Email
	@Size(min = 1)
	String name;
}
