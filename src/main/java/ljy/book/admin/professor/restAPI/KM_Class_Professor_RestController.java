package ljy.book.admin.professor.restAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.resource.Km_classResource;
import ljy.book.admin.entity.resource.Km_signUpClassForStuResource;
import ljy.book.admin.professor.service.impl.KM_Class_Professor_Service;
import ljy.book.admin.professor.service.impl.KM_FileUploadDownload_Professor_Service;
import ljy.book.admin.request.KM_classVO;
import ljy.book.admin.request.KM_signUpClassForStuVO;
import ljy.book.admin.security.CurrentKm_User;

@RestController
@RequestMapping("/api/professor/class")
public class KM_Class_Professor_RestController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	KM_Class_Professor_Service km_classService;

	@Autowired
	KM_FileUploadDownload_Professor_Service fileUploadService;

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

	@GetMapping("/{idx}/signUpList")
	@Memo("수업을 신청한 명단을 가져오는 메소드")
	public ResponseEntity<?> getSignUpClassForStu(@PathVariable long idx, @CurrentKm_User KM_user km_user) {
		List<KM_signUpClassForStuVO> list = km_classService.getSignUpClassList(idx, km_user.getId());
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> links = new HashMap<String, Object>();
		links.put("self", linkBuilder.withSelfRel());
		links.put("profile", new Link("/docs/index.html"));
		result.put("_embedded", list);
		result.put("_links", links);
		return ResponseEntity.ok(result);
	}

	@GetMapping
	@Memo("해당 교수의 수업 리스트를 가져오는 메소드")
	public ResponseEntity<?> getClassListPage(Pageable pageable, PagedResourcesAssembler<KM_class> paged,
		@CurrentKm_User KM_user km_user) {
		Page<KM_class> page = this.km_classService.getClassListPage(km_user.getId(), pageable);
		lombok.var pagedResources = paged.toModel(page, e -> new Km_classResource(modelMapper.map(e, KM_classVO.class)));
		pagedResources.add(new Link("/docs/index.html").withRel("profile"));
		pagedResources.add(linkBuilder.slash("/{seq}").withRel("delete"));
		pagedResources.add(linkBuilder.slash("/{seq}").withRel("update"));
		return ResponseEntity.ok(pagedResources);
	}

	@GetMapping("/{idx}")
	@Memo("해당 교수의 수업 리스트 중 특정 수업의 정보를 가져오는 메소드")
	public ResponseEntity<?> getClassInfo(@PathVariable long idx, @CurrentKm_User KM_user km_user) {
		KM_classVO km_class = km_classService.getClassInfo(idx, km_user.getId());
		Km_classResource km_classResource = new Km_classResource(km_class);
		km_classResource.add(linkBuilder.slash(km_class.getSeq()).withRel("delete"));
		km_classResource.add(linkBuilder.slash(km_class.getSeq()).withRel("update"));
		km_classResource.add(new Link("/docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypes.HAL_JSON).body(km_classResource);
	}

	@PostMapping
	@Memo("해당 교수의 수업을 등록")
	public ResponseEntity<?> save(@Valid @RequestBody KM_classVO km_classVO, Errors errors, @CurrentKm_User KM_user km_user) {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		Km_classResource km_classResource = new Km_classResource(km_classVO);
		km_classValidator.validate(km_classVO, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		km_classResource.add(linkBuilder.slash("").withRel("update"));
		km_classResource.add(linkBuilder.slash("").withRel("delete"));
		KM_class km_class = km_classService.save(modelMapper.map(km_classVO, KM_class.class), km_user.getId());
		km_classVO.setSeq(km_class.getSeq());
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypes.HAL_JSON).body(km_classResource);
	}

	@PutMapping("/{seq}/signUpList/{signUpIdx}")
	@Memo("해당 수업신청 승인")
	public ResponseEntity<?> signUpClassForStuSuccess(@PathVariable long seq, @PathVariable long signUpIdx,
		@CurrentKm_User KM_user km_user) {
		if (!km_classService.checkByKm_user(seq, "dlwodyd202"))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		km_classService.signUpClassSuccess(seq, signUpIdx);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping
	@Memo("해당 교수의 수업을 수정")
	public ResponseEntity<?> update(@Valid @RequestBody KM_classVO km_classVO, Errors errors, @CurrentKm_User KM_user km_user) {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		Km_classResource km_classResource = new Km_classResource(km_classVO);
		km_classValidator.validate(km_classVO, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		km_classResource.add(linkBuilder.slash("").withRel("update"));
		km_classResource.add(linkBuilder.slash("").withRel("delete"));
		KM_class km_class = km_classService.update(modelMapper.map(km_classVO, KM_class.class), km_user.getId());
		km_classVO.setSeq(km_class.getSeq());
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypes.HAL_JSON).body(km_classResource);
	}
}
