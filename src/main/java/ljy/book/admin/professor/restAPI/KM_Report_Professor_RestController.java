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
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
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

import ljy.book.admin.common.object.CustomSearchObject;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.dto.validate.Km_reportValidator;
import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.entity.resource.Km_reportResource;
import ljy.book.admin.professor.service.impl.KM_Class_Professor_Service;
import ljy.book.admin.professor.service.impl.KM_FileUploadDownload_Professor_Service;
import ljy.book.admin.professor.service.impl.KM_Report_Professor_Service;
import ljy.book.admin.request.KM_reportVO;
import ljy.book.admin.security.CurrentKm_User;

@RestController
@RequestMapping("api/professor/report")
public class KM_Report_Professor_RestController {

	Logger log = LoggerFactory.getLogger(KM_Report_Professor_RestController.class);

	@Autowired
	KM_Report_Professor_Service km_ReportService;

	@Autowired
	KM_FileUploadDownload_Professor_Service km_fileUploadDownloadService;

	@Autowired
	Km_reportValidator km_reportValidator;

	@Autowired
	KM_Class_Professor_Service km_classService;

	@Autowired
	ModelMapper modelMapper;

	ControllerLinkBuilder linkBuilder;

	@PostConstruct
	public void init() {
		linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
	}

	@GetMapping("{reportIdx}")
	@Memo("해당 과제의 정보를 가져오는 메소드")
	public ResponseEntity<?> getReport(@PathVariable long reportIdx, @CurrentKm_User KM_user km_user) {
		try {
			Km_reportResource km_reportResource = new Km_reportResource(km_ReportService.getReport(reportIdx, km_user.getId()));
			km_reportResource.add(linkBuilder.slash(reportIdx).withRel("delete"));
			km_reportResource.add(linkBuilder.slash(reportIdx).withRel("update"));
			km_reportResource.add(new Link("/docs/index.html").withRel("profile"));
			return ResponseEntity.status(HttpStatus.OK).body(km_reportResource);
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/{reportIdx}/fileList")
	@Memo("해당 과제의 파일 및 이미지 리스트를 가져오는 메소드")
	public ResponseEntity<?> getReportFileList(@PathVariable long reportIdx, @CurrentKm_User KM_user km_user) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("reportFileAndImgList", km_ReportService.getReportFileList(reportIdx, km_user.getId()));
		HashMap<String, Object> _links = new HashMap<String, Object>();
		_links.put("profile", new Link("/docs/index.html").withRel("profile"));
		result.put("_links", _links);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/class/{classIdx}/list")
	@Memo("해당 클래스의 과제  리스트를 가져오는 메소드")
	public ResponseEntity<?> getReportList(@PathVariable long classIdx, Pageable pageable, CustomSearchObject customsearchObj,
		@CurrentKm_User KM_user km_user) {
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
			km_reportResource.add(linkBuilder.slash(data.getSeq()).withRel("delete"));
			km_reportResource.add(linkBuilder.slash(data.getSeq()).withRel("update"));
			resultList.add(km_reportResource);
		}
		result.put("list", resultList);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PostMapping("{classIdx}")
	@Memo("해당 클래스의 과제를 등록하는 메소드")
	public ResponseEntity<?> save(@PathVariable long classIdx, @RequestBody @Valid KM_reportVO km_reportVO, Errors errors,
		@CurrentKm_User KM_user km_user) {
		km_reportValidator.validate(km_reportVO, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		if (!km_classService.checkByKm_user(classIdx, km_user.getId())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		km_reportVO.setClassIdx(classIdx);
		KM_Report km_report = km_ReportService.save(km_reportVO);
		km_reportVO.setSeq(km_report.getSeq());
		Km_reportResource km_reportResource = new Km_reportResource(km_reportVO);
		km_reportResource.add(linkBuilder.slash(km_report.getSeq()).withRel("delete"));
		km_reportResource.add(linkBuilder.slash(km_report.getSeq()).withRel("update"));
		km_reportResource.add(new Link("/docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.OK).body(km_reportResource);
	}

	@PutMapping("{reportIdx}")
	@Memo("해당 클래스의 과제를 수정하는 메소드")
	public ResponseEntity<?> update(@PathVariable long reportIdx, @RequestBody @Valid KM_reportVO km_reportVO, Errors errors,
		@CurrentKm_User KM_user km_user) {
		if (!km_ReportService.checkBySeqAndUserId(reportIdx, "dlwodyd202")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		km_reportValidator.validate(km_reportVO, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		km_ReportService.update(reportIdx, km_reportVO);
		km_reportVO.setSeq(reportIdx);
		Km_reportResource km_reportResource = new Km_reportResource(km_reportVO);
		km_reportResource.add(linkBuilder.slash(km_reportVO.getSeq()).withRel("delete"));
		km_reportResource.add(linkBuilder.slash(km_reportVO.getSeq()).withRel("update"));
		km_reportResource.add(new Link("/docs/index.html").withRel("profile"));
		return ResponseEntity.status(HttpStatus.OK).body(km_reportResource);
	}

}
