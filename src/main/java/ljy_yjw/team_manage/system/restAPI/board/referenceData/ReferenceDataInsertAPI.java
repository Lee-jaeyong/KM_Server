package ljy_yjw.team_manage.system.restAPI.board.referenceData;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.dto.ReferenceDataDTO;
import ljy_yjw.team_manage.system.domain.entity.ReferenceData;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.ReferenceDataController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.insert.referenceData.ReferenceDataOneInsertService;
import lombok.var;

@ReferenceDataController
public class ReferenceDataInsertAPI {
	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	ReferenceDataOneInsertService referenceDataOneInsertService;

	@PostMapping("/{code}")
	@Memo("참고자료를 등록하는 메소드")
	public ResponseEntity<?> save(@PathVariable String code, @RequestBody @Valid ReferenceDataDTO referenceData,
		@Current_User Users user, Errors error) throws TeamCodeNotFountException, InputValidException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		teamAuthService.checkTeamAuth(user, code);
		Team team = teamAuthService.getTeamObject(code);
		ReferenceData saveReferenceData = referenceDataOneInsertService
			.insertReferenceData(referenceData.parseThis2ReferenceData(user, team));
		var result = new CustomEntityModel<>(saveReferenceData, this, Long.toString(saveReferenceData.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}
}
