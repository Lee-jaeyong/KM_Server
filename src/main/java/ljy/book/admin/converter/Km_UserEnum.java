package ljy.book.admin.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ljy.book.admin.entity.enums.UserRule;

@Component
public class Km_UserEnum implements Formatter<UserRule> {

	@Override
	public String print(UserRule object, Locale locale) {
		return object.toString();
	}

	@Override
	public UserRule parse(String text, Locale locale) throws ParseException {
		return UserRule.valueOf(text.toUpperCase());
	}

}
