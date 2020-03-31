package ljy.book.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KM_signUpClassForStuVO {
	Long seq;
	boolean signUp_state;
	String date;
	String userId;
}
