package ljy_yjw.team_manage.system.domain.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.ibatis.type.Alias;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonInclude;

import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.domain.enums.UserRule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Alias("UserDTO")
public class UserDTO {

	@NotNull(message = "아이디를 입력해주세요.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문과 숫자 조합으로 입력해주세요.")
	@Size(max = 20, min = 6, message = "아이디는 6자 이상, 20자 이하로 입력해주세요.")
	String id;

	@NotNull(message = "이름을 입력해주세요.")
	@Pattern(regexp = "^[가-힣]*$", message = "이름은 한글만 입력해주세요.")
	String name;

	@NotNull(message = "비밀번호를 입력해주세요.")
	@Size(max = 15, min = 9, message = "비밀번호는 9자 이상, 15자 이하로 입력해주세요.")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String pass;

	@NotNull(message = "이메일을 입력해주세요.")
	@Email(message = "이메일을 양식이 올바르지 않습니다.")
	@Size(max = 20, min = 1, message = "이메일은 1자 이상 20자 이하로 입력해주세요.")
	String email;
	String img;

	public Users parseThis2User(PasswordEncoder encoder) {
		Users user = new Users();
		user.setId(this.id);
		user.setName(this.name);
		user.setEmail(this.email);
		if (encoder != null) {
			user.setPass(encoder.encode(this.pass));
		}
		Set<UserRule> rule = new HashSet<UserRule>();
		rule.add(UserRule.USER);
		user.setUserRule(rule);
		return user;
	}
}
