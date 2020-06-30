package ljy_yjw.team_manage.system.restAPI.board.referenceData;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.dto.ReferenceDataDTO;
import ljy_yjw.team_manage.system.domain.entity.ReferenceData;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.InputValidException;
import ljy_yjw.team_manage.system.exception.object.ErrorResponse;
import ljy_yjw.team_manage.system.restAPI.ReferenceDataController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.referenceData.ReferenceDataAuthService;
import ljy_yjw.team_manage.system.service.update.referenceData.ReferenceDataOneUpdateService;
import lombok.var;

@ReferenceDataController
public class ReferenceDataUpdateAPI {

	@Autowired
	ReferenceDataAuthService referenceDataAuthService;

	@Autowired
	ReferenceDataOneUpdateService referenceDataOneUpdateService;

	@PutMapping("/{seq}")
	@Memo("참고자료를 수정하는 메소드")
	public ResponseEntity<?> update(@PathVariable long seq, @RequestBody @Valid ReferenceDataDTO referenceData,
		@Current_User Users user, Errors error) throws InputValidException, NotFoundException {
		if (error.hasErrors()) {
			throw new InputValidException(ErrorResponse.parseFieldError(error.getFieldErrors()));
		}
		referenceDataAuthService.boardAuthCheck(seq, user.getId());
		ReferenceData updateReferenceData = referenceData.parseThis2ReferenceData(user, null);
		updateReferenceData = referenceDataOneUpdateService.updateReferenceData(seq, updateReferenceData);
		var result = new CustomEntityModel<>(updateReferenceData, this, Long.toString(updateReferenceData.getSeq()), Link.ALL);
		return ResponseEntity.ok(result);
	}
}
