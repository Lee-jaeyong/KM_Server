package ljy.book.admin.webConfig.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ljy.book.admin.professor.requestDTO.TeamDTO;

@Component
public class TeamConverter implements Formatter<TeamDTO> {

	@Override
	public String print(TeamDTO object, Locale locale) {
		return Long.toString(object.getSeq());
	}

	@Override
	public TeamDTO parse(String text, Locale locale) throws ParseException {
		TeamDTO team = new TeamDTO();
		team.setSeq(Long.parseLong(text));
		return team;
	}

}
