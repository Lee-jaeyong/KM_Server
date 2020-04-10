package ljy.book.admin.student.restAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy.book.admin.common.object.CustomSearchObject;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.resource.Km_reportResource;
import ljy.book.admin.request.KM_reportVO;
import ljy.book.admin.security.CurrentKm_User;
import ljy.book.admin.student.service.impl.KM_Class_Student_Service;
import ljy.book.admin.student.service.impl.KM_Report_Student_Service;

@RestController
@RequestMapping("/api/student/report")
public class KM_report_Student_RestController {

	@Autowired
	KM_Class_Student_Service km_classService;

	@Autowired
	KM_Report_Student_Service km_ReportService;

	ControllerLinkBuilder linkBuilder;

	@PostConstruct
	public void init() {
		linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
	}

	@GetMapping("/{idx}")
	public ResponseEntity<?> getReportInfo(@PathVariable long idx, @CurrentKm_User KM_user km_user) {
		KM_reportVO km_report = km_ReportService.getReport(idx, "dlwodyd202");
		if (km_report == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Km_reportResource resource = new Km_reportResource(km_report);
		resource.add(new Link("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(resource);
	}

	@GetMapping("/class/{classIdx}/list")
	@Memo("해당 클래스의 과제  리스트를 가져오는 메소드")
	public ResponseEntity<?> getReportList(@PathVariable long classIdx, Pageable pageable, CustomSearchObject customsearchObj,
		@CurrentKm_User KM_user km_user) {
		if (km_classService.checkByKm_user(classIdx, km_user.getId()) == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		List<Object> resultList = new ArrayList<Object>();
		result.put("totalCount", km_ReportService.getTotalCount(classIdx, customsearchObj, km_user.getId()));
		for (KM_Report c : km_ReportService.getReportList(classIdx, pageable, customsearchObj, km_user.getId())) {
			KM_reportVO data = new KM_reportVO();
			data.setClassIdx(classIdx);
			data.setContent(c.getContent());
			data.setEndDate(c.getEndDate());
			data.setHit(c.getHit());
			data.setName(c.getName());
			data.setSeq(c.getSeq());
			data.setStartDate(c.getStartDate());
			data.setUseSubmitDates(c.getSubmitOverDue_state());
			Km_reportResource km_reportResource = new Km_reportResource(data);
			resultList.add(km_reportResource);
		}

		result.put("list", resultList);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
