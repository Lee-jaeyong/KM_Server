package ljy.book.admin.restAPI;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping
	public ResponseEntity getClassList() {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		List<KM_classVO> list = km_classService.getClassList();
		List<Object> result = new ArrayList<Object>();
		for (KM_classVO listData : list) {
			Km_classResource km_classResource = new Km_classResource(listData);
			km_classResource.add(linkBuilder.slash(listData.getSeq()).withRel("delete").withDeprecation("삭제"));
			km_classResource.add(linkBuilder.slash(listData.getSeq()).withRel("update").withDeprecation("수정"));
			result.add(km_classResource);
		}
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypes.HAL_JSON).body(result);
	}

	@GetMapping("{idx}")
	public ResponseEntity getClassInfo(@PathVariable long idx) {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		KM_classVO km_class = km_classService.getClassInfo(idx);
		Km_classResource km_classResource = new Km_classResource(km_class);
		km_classResource.add(linkBuilder.slash(km_class.getSeq()).withRel("delete").withDeprecation("삭제"));
		km_classResource.add(linkBuilder.slash(km_class.getSeq()).withRel("update").withDeprecation("수정"));
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypes.HAL_JSON).body(km_classResource);
	}

	@PostMapping
	public ResponseEntity save(@Valid @RequestBody KM_classVO km_classVO, Errors errors) {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		Km_classResource km_classResource = new Km_classResource(km_classVO);
		km_classValidator.validate(km_classVO, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		km_classResource.add(linkBuilder.slash("").withRel("update").withDeprecation("수정"));
		km_classResource.add(linkBuilder.slash("").withRel("delete").withDeprecation("삭제"));
		KM_class km_class = km_classService.save(modelMapper.map(km_classVO, KM_class.class));
		km_classVO.setSeq(km_class.getSeq());
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypes.HAL_JSON).body(km_classResource);
	}

	@PutMapping
	public ResponseEntity update(@Valid @RequestBody KM_classVO km_classVO, Errors errors) {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		Km_classResource km_classResource = new Km_classResource(km_classVO);
		km_classValidator.validate(km_classVO, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		km_classResource.add(linkBuilder.slash("").withRel("update").withDeprecation("수정"));
		km_classResource.add(linkBuilder.slash("").withRel("delete").withDeprecation("삭제"));
		KM_class km_class = km_classService.update(modelMapper.map(km_classVO, KM_class.class));
		km_classVO.setSeq(km_class.getSeq());
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaTypes.HAL_JSON).body(km_classResource);
	}

//	@GetMapping("{userID}")
//	public ResponseEntity findByUserId(@PathVariable String userID) {
//		Km_classDTO km_classDTO = new Km_classDTO();
//		Km_classResource km_classResource = new Km_classResource(km_classDTO);
//		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
//		KM_user km_user = new KM_user();
//		//km_user.setId(userID);
//		km_classResource.add(linkBuilder.slash("").withRel("add_class"));
//		km_classResource.add(linkBuilder.slash("").withRel("update_class"));
//		km_classResource.add(linkBuilder.slash("").withRel("delete_class"));
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		result.put("href", km_classResource);
//		result.put("classInfo", km_classService.findByUserId(km_user));
//		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
//	}
//
//	@PostMapping
//	public ResponseEntity save(@RequestBody @Valid Km_classDTO km_classDTO, Errors error, HttpSession session) {
//		KM_subject km_subject = new KM_subject();
//		km_subject.setSeq(km_classDTO.getSubject_seq());
//		km_subjectValidator.validate(km_subject, error);
//		km_classValidator.validate(km_classDTO, error);
//		if (error.hasErrors()) {
//			return whenHasError(km_classDTO, error);
//		}
//
//		Km_classResource km_classResource = new Km_classResource(km_classDTO);
//		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
//		if (error.hasErrors()) {
//			return whenHasError(km_classDTO, error);
//		}
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		result.put("href", km_classResource);
//		result.put("km_classInfo", km_classService.save(km_classDTO, session));
//		km_classResource.add(linkBuilder.slash("fileupload").withRel("fileUpload"));
//		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
//	}
//
//	@PostMapping("fileupload/{classIdx}")
//	public ResponseEntity saveFile(MultipartFile file, @PathVariable long classIdx) {
//		fileUploadService.storeFile(file, "addClass", classIdx);
//		return ResponseEntity.status(HttpStatus.OK).build();
//	}
//
//	public ResponseEntity whenHasError(Km_classDTO km_classDTO, Errors error) {
//		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
//		Km_classResource km_classResource = new Km_classResource(km_classDTO);
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		result.put("error", error.getAllErrors());
//		result.put("href", km_classResource);
//		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
//	}
}
