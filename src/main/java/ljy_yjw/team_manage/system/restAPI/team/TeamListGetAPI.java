package ljy_yjw.team_manage.system.restAPI.team;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.restAPI.TeamController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.read.team.TeamReadService;
import ljy_yjw.team_manage.system.service.update.user.UserSettingService;
import lombok.var;

@TeamController
public class TeamListGetAPI {

	@Autowired
	TeamReadService teamReadService;

	@Autowired
	UserSettingService userSettingService;

	@Memo("자신이 팀장이고 아직 만료되지 않은 팀의 정보를 모두 가져오기")
	@GetMapping("/unFinished-mylist")
	public ResponseEntity<?> getMyTeamList(@Current_User Users user) {
		CollectionModel<Team> result = new CollectionModel<>(teamReadService.getMyTeamList(user.getId()));
		return ResponseEntity.ok(result);
	}

	@Memo("자신이 소속되어있는 모든 팀 정보 가져오기")
	@GetMapping
	public ResponseEntity<?> getJoinTeamFinished(@Current_User Users user, @RequestParam(required = false) String flag) {
		var result = new CollectionModel<>(settingCustomEntityModel(flag, user.getId()));
		return ResponseEntity.ok(result);
	}
	
	@Memo("EntityModel 세팅")
	private List<CustomEntityModel<Team>> settingCustomEntityModel(String flag, String id) {
		List<Team> result = null;
		if (flag != null && flag.equals("finished")) {
			result = teamReadService.getTeamListFinished(id);
			return result.stream().map(c -> new CustomEntityModel<>(c, this, c.getCode(), Link.NOT_INCLUDE))
				.collect(Collectors.toList());
		} else if (flag != null && flag.equals("son")) {
			result = teamReadService.getTeamFinishToday(id);
			return result.stream().map(c -> new CustomEntityModel<>(c, this, c.getCode(), Link.NOT_INCLUDE))
				.collect(Collectors.toList());
		} else {
			result = teamReadService.getTeamListUnFinished(id);
			return result.stream()
				.map(c -> c.getTeamLeader().getId().equals(id) ? new CustomEntityModel<>(c, this, c.getCode(), Link.ALL)
					: new CustomEntityModel<>(c, this, c.getCode(), Link.NOT_INCLUDE))
				.collect(Collectors.toList());
		}
	}

	private void teamLeaderImgSetting(List<Team> list) {
		list.forEach(c -> {
			if (c.getTeamLeader().getImg() != null) {
				try {
					userSettingService.imgSetting(c.getTeamLeader());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
