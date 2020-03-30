package ljy.book.admin.restAPI;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.dto.validate.Km_classValidator;
import ljy.book.admin.dto.validate.Km_subjectValidator;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.resource.Km_classResource;
import ljy.book.admin.request.KM_classVO;
import ljy.book.admin.service.KM_ClassService;
import ljy.book.admin.service.KM_FileUploadDownloadService;

@RestController
@RequestMapping("professor/class")
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
	@Memo("해당 교수의 수업 리스트를 가져오는 메소드")
	public ResponseEntity<?> getClassListPage(Pageable pageable, PagedResourcesAssembler<KM_class> paged) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		auth = SecurityContextHolder.getContext().getAuthentication();
		Page<KM_class> page = this.km_classService.getClassListPage(auth.getName(), pageable);
		lombok.var pagedResources = paged.toModel(page, e -> new Km_classResource(modelMapper.map(e, KM_classVO.class)));
		pagedResources.add(new Link("/docs/index.html").withRel("profile"));
		pagedResources.add(linkBuilder.slash("/{seq}").withRel("delete"));
		pagedResources.add(linkBuilder.slash("/{seq}").withRel("update"));
		return ResponseEntity.ok(pagedResources);
	}

	@GetMapping("/{idx}")
	@Memo("해당 교수의 수업 리스트 중 특정 수업의 정보를 가져오는 메소드")
	public ResponseEntity getClassInfo(@PathVariable long idx) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		KM_classVO km_class = km_classService.getClassInfo(idx, auth.getName());
		Km_classResource km_classResource = new Km_classResource(km_class);
		km_classResource.add(linkBuilder.slash(km_class.getSeq()).withRel("delete"));
		km_classResource.add(linkBuilder.slash(km_class.getSeq()).withRel("update"));
		km_classResource.add(new Link("/docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypes.HAL_JSON).body(km_classResource);
	}

	@PostMapping
	@Memo("해당 교수의 수업을 등록")
	public ResponseEntity save(@Valid @RequestBody KM_classVO km_classVO, Errors errors) {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		Km_classResource km_classResource = new Km_classResource(km_classVO);
		km_classValidator.validate(km_classVO, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		km_classResource.add(linkBuilder.slash("").withRel("update"));
		km_classResource.add(linkBuilder.slash("").withRel("delete"));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		KM_class km_class = km_classService.save(modelMapper.map(km_classVO, KM_class.class), auth.getName());
		km_classVO.setSeq(km_class.getSeq());
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypes.HAL_JSON).body(km_classResource);
	}

	@PutMapping
	@Memo("해당 교수의 수업을 수정")
	public ResponseEntity update(@Valid @RequestBody KM_classVO km_classVO, Errors errors) {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		Km_classResource km_classResource = new Km_classResource(km_classVO);
		km_classValidator.validate(km_classVO, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		km_classResource.add(linkBuilder.slash("").withRel("update"));
		km_classResource.add(linkBuilder.slash("").withRel("delete"));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		KM_class km_class = km_classService.update(modelMapper.map(km_classVO, KM_class.class), auth.getName());
		km_classVO.setSeq(km_class.getSeq());
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypes.HAL_JSON).body(km_classResource);
	}
}
