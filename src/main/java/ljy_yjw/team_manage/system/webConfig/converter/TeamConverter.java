package ljy_yjw.team_manage.system.webConfig.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ljy_yjw.team_manage.system.domain.dto.TeamDTO;

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
