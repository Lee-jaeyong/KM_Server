package ljy_yjw.team_manage.system.custom.anotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

@Target(METHOD)
public @interface Memo {
	String value();
}
