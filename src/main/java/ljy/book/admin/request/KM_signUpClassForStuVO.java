package ljy.book.admin.request;

import ljy.book.admin.entity.KM_user;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KM_signUpClassForStuVO {
	Long seq;
	String signUp_state;
	String date;
	KM_user km_user;
}
