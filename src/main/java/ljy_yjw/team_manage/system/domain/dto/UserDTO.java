package ljy_yjw.team_manage.system.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Alias("UserDTO")
public class UserDTO {

	@NotNull(message = "아이디를 입력해주세요")
	String id;
	@NotNull(message = "이름을 입력해주세요")
	String name;
	@NotNull(message = "비밀번호를 입력해주세요")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String pass;
	@NotNull(message = "이메일을 입력해주세요")
	@Email(message = "이메일을 양식이 올바르지 않습니다")
	String email;
	String img;
}
