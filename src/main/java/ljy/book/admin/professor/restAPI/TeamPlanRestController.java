package ljy.book.admin.professor.restAPI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.Users;
import ljy.book.admin.professor.requestDTO.PlanByUserDTO;
import ljy.book.admin.professor.requestDTO.TeamDTO;
import ljy.book.admin.security.Current_User;

@RestController
@RequestMapping("/api/teamManage/plan")
public class TeamPlanRestController {

	@Memo("일정을 등록하는 메소드")
	@PostMapping("/{seq}")
	public ResponseEntity<?> save(@PathVariable TeamDTO seq, @RequestBody PlanByUserDTO planByUser, @Current_User Users user) {
		return ResponseEntity.ok("");
	}
}
