package ljy_yjw.team_manage.system.restAPI.board.referenceData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javassist.NotFoundException;
import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.domain.entity.ReferenceData;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.restAPI.ReferenceDataController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.referenceData.ReferenceDataAuthService;
import ljy_yjw.team_manage.system.service.delete.referenceData.ReferenceDataOneDeleteService;
import lombok.var;

@ReferenceDataController
public class ReferenceDataDeleteAPI {

	@Autowired
	ReferenceDataAuthService referenceDataAuthService;

	@Autowired
	ReferenceDataOneDeleteService referenceDataOneDeleteService;

	@DeleteMapping("/{seq}")
	@Memo("참고자료를 삭제하는 메소드")
	public ResponseEntity<?> delete(@PathVariable long seq, @Current_User Users user) throws NotFoundException {
		referenceDataAuthService.boardAuthCheck(seq, user.getId());
		ReferenceData deleteReferenceData = referenceDataOneDeleteService.deleteReferenceData(seq);
		var result = new CustomEntityModel<>(deleteReferenceData, this, Long.toString(deleteReferenceData.getSeq()),
			Link.NOT_INCLUDE);
		return ResponseEntity.ok(result);
	}

}
