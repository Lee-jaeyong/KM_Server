package ljy_yjw.team_manage.system.restAPI.plan;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import ljy_yjw.team_manage.system.custom.anotation.Memo;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.CheckInputValidException;
import ljy_yjw.team_manage.system.exception.exceptions.team.TeamCodeNotFountException;
import ljy_yjw.team_manage.system.restAPI.PlanController;
import ljy_yjw.team_manage.system.security.Current_User;
import ljy_yjw.team_manage.system.service.auth.team.TeamAuthService;
import ljy_yjw.team_manage.system.service.insert.plan.PlanByUserExcelInsertService;
import ljy_yjw.team_manage.system.service.read.plan.PlanExcelReadService;
import ljy_yjw.team_manage.system.service.read.plan.PlanReadService;
import ljy_yjw.team_manage.system.service.read.team.TeamReadService;
import lombok.var;

@PlanController
public class PlanFileUploadAPI {

	@Autowired
	TeamAuthService teamAuthService;

	@Autowired
	TeamReadService teamReadService;

	@Autowired
	PlanExcelReadService planExcelReadService;

	@Autowired
	PlanReadService planReadService;

	@Autowired
	PlanByUserExcelInsertService planByUserExcelInsertService;

	@Memo("엑셀 데이터 가져오기")
	@PostMapping("/{code}/excel-data")
	public ResponseEntity<?> getExcelData(@PathVariable String code, MultipartFile file, @Current_User Users user)
		throws CanNotPerformException, IOException, CheckInputValidException, TeamCodeNotFountException {
		teamAuthService.checkTeamAuth(user, code); // 팀 코드가 존재하지 않으면 TeamCodeNotFoundException 발생
		Team team = teamReadService.getTeamByCode(code);
		List<Users> joinUser = new ArrayList<>();
		team.getJoinPerson().forEach(c -> {
			joinUser.add(c.getUser());
		});
		joinUser.add(user);
		List<PlanByUser> plans = planExcelReadService.excelDataRead(file, joinUser, team);
		var result = new CollectionModel<PlanByUser>(plans);
		result.add(linkTo(this.getClass()).slash("docs/index.html").withRel("profile"));
		return ResponseEntity.ok(result);
	}

	@Memo("엑셀 양식 다운로드")
	@GetMapping("/excel-form")
	public ResponseEntity<?> excelFormDownload(@Current_User Users user, HttpServletRequest request)
		throws CanNotPerformException {
		Resource resource = planReadService.excelFormDown();
		if (resource == null) {
			throw new CanNotPerformException("죄송합니다. 잠시 에러가 발생했습니다. 잠시후에 다시 시도해주세요.");
		}
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
	}

	@Memo("엑셀파일 업로드")
	@PostMapping("/{code}/excel-upload")
	public ResponseEntity<?> excelFileUpload(@PathVariable String code, MultipartFile file, @Current_User Users user)
		throws CanNotPerformException, IOException, TeamCodeNotFountException, CheckInputValidException {
		teamAuthService.checkTeamAuth(user, code); // 팀 코드가 존재하지 않으면 TeamCodeNotFoundException 발생
		Team team = teamReadService.getTeamByCode(code);
		List<Users> joinUser = new ArrayList<>();
		team.getJoinPerson().forEach(c -> {
			joinUser.add(c.getUser());
		});
		joinUser.add(user);
		return ResponseEntity.ok(planByUserExcelInsertService.excelFileUpload(file, joinUser, team));
	}
}
