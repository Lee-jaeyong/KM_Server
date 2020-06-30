package ljy_yjw.team_manage.system.restAPI.board.referenceData;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.entity.ReferenceData;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.ReferenceDataController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.read.referenceData.ReferenceDataReadService;
import ljy_yjw.team_manage.system.service.update.referenceData.ReferenceDataSettingService;
import ljy_yjw.team_manage.system.service.update.user.UserSettingService;

@ReferenceDataController
public class ReferenceDataOneGetAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	ReferenceDataReadService referenceDataReadService;

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	ReferenceDataSettingService referenceDataSettingService;

	@GetMapping("/{seq}")
	@Memo("참고자료 단건 조회 메소드")
	public ResponseEntity<?> getOne(@PathVariable long seq, @Current_User Users user)
		throws NotFoundException, TeamCodeNotFountException, IOException {
		ReferenceData refereneceData = referenceDataReadService.getReferenceData(seq);
		teamAuthService.checkTeamAuth(user, refereneceData.getTeam().getCode());
		userSettingService.imgSetting(refereneceData.getUser());
		referenceDataSettingService.settingImg(refereneceData);
		return ResponseEntity.ok(getCustomEntityModel(refereneceData, user));
	}

	private CustomEntityModel<ReferenceData> getCustomEntityModel(ReferenceData referenceData, Users user) {
		if (referenceData.getUser().getId().equals(user.getId()))
			return new CustomEntityModel<ReferenceData>(referenceData, this, Long.toString(referenceData.getSeq()), Link.ALL);
		else
			return new CustomEntityModel<ReferenceData>(referenceData, this, Long.toString(referenceData.getSeq()),
				Link.NOT_INCLUDE);
	}
}
