package ljy.book.admin.student.restAPI;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.dto.validate.Km_classValidator;
import ljy.book.admin.dto.validate.Km_subjectValidator;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.resource.Km_classResource;
import ljy.book.admin.professor.service.impl.KM_ClassService;
import ljy.book.admin.professor.service.impl.KM_FileUploadDownloadService;
import ljy.book.admin.request.KM_classVO;
import ljy.book.admin.security.CurrentKm_User;

@RestController
@RequestMapping("/api/student/class")
public class KM_ClassRestController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	KM_ClassService km_classService;

	@Autowired
	KM_FileUploadDownloadService fileUploadService;

	@Autowired
	Km_subjectValidator km_subjectValidator;

	@Autowired
	Km_classValidator km_classValidator;

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
		Page<KM_class> page = this.km_classService.getClassListPage(km_user.getId(), pageable);
		lombok.var pagedResources = paged.toModel(page, e -> new Km_classResource(modelMapper.map(e, KM_classVO.class)));
		pagedResources.add(new Link("/docs/index.html").withRel("profile"));
		pagedResources.add(linkBuilder.slash("/{seq}").withRel("delete"));
		pagedResources.add(linkBuilder.slash("/{seq}").withRel("update"));
		return ResponseEntity.ok(pagedResources);
	}
}
