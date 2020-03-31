package ljy.book.admin.student.restAPI;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.resource.Km_classResource;
import ljy.book.admin.entity.resource.Km_signUpClassForStuResource;
import ljy.book.admin.request.KM_classVO;
import ljy.book.admin.security.CurrentKm_User;
import ljy.book.admin.student.service.impl.KM_Class_Student_Service;

@RestController
@RequestMapping("/api/student/class")
public class KM_Class_Student_RestController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	KM_Class_Student_Service km_classService;

	@Autowired
	ModelMapper modelMapper;

	ControllerLinkBuilder linkBuilder;

	@PostConstruct
	public void init() {
		linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
	}

	@GetMapping
	@Memo("해당 학생의 수업 리스트를 가져오는 메소드")
	public ResponseEntity<?> getClassListPage(Pageable pageable, PagedResourcesAssembler<KM_class> paged,
		@CurrentKm_User KM_user km_user) {
		Page<KM_class> page = this.km_classService.getClassListPage("dlwodyd202", pageable);
		lombok.var pagedResources = paged.toModel(page, e -> new Km_classResource(modelMapper.map(e, KM_classVO.class)));
		pagedResources.add(new Link("/docs/index.html").withRel("profile"));
		return ResponseEntity.ok(pagedResources);
	}

	@PostMapping("/{code}")
	@Memo("해당 학생이 수업을 신청하는 메소드")
	public ResponseEntity<?> signUpClassForStu(@PathVariable String code, @CurrentKm_User KM_user km_user) {
		KM_classVO km_class = km_classService.getClassInfo(0, code);
		if (km_class == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Km_signUpClassForStuResource resource = new Km_signUpClassForStuResource(
			km_classService.signUpClassForStu(km_class.getSeq(), km_user.getId()));
		resource.add(new Link("/docs/index.html").withRel("profile"));
		resource.add(linkBuilder.slash("/{seq}").withRel("delete"));
		return ResponseEntity.status(HttpStatus.OK).body(resource);
	}
}
