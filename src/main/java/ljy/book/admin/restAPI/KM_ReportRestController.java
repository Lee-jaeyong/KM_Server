package ljy.book.admin.restAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
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

import ljy.book.admin.dto.validate.Km_reportValidator;
import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.entity.resource.Km_reportResource;
import ljy.book.admin.request.KM_reportVO;
import ljy.book.admin.service.KM_FileUploadDownloadService;
import ljy.book.admin.service.KM_ReportService;

@RestController
@RequestMapping("report")
public class KM_ReportRestController {

	Logger log = LoggerFactory.getLogger(KM_ReportRestController.class);

	@Autowired
	KM_ReportService km_ReportService;

	@Autowired
	KM_FileUploadDownloadService km_fileUploadDownloadService;

	@Autowired
	Km_reportValidator km_reportValidator;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping("{reportIdx}")
	public ResponseEntity getReport(@PathVariable long reportIdx) {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		Km_reportResource km_reportResource = new Km_reportResource(km_ReportService.getReport(reportIdx));
		km_reportResource.add(linkBuilder.slash(reportIdx).withRel("delete").withDeprecation("삭제"));
		km_reportResource.add(linkBuilder.slash(reportIdx).withRel("update").withDeprecation("수정"));
		return ResponseEntity.status(HttpStatus.OK).body(km_reportResource);
	}

	@GetMapping("{reportIdx}/fileList")
	public ResponseEntity getReportFileList(@PathVariable long reportIdx) {
		return ResponseEntity.status(HttpStatus.OK).body(km_ReportService.getReportFileList(reportIdx));
	}

	@GetMapping("{classIdx}/list")
	public ResponseEntity getReportList(@PathVariable long classIdx, Pageable pageable) {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<Object> resultList = new ArrayList<Object>();
		result.put("totalCount", km_ReportService.getTotalCount(classIdx));
		for (KM_Report c : km_ReportService.getReportList(classIdx, pageable)) {
			KM_reportVO data = new KM_reportVO();
			data.setClassIdx(classIdx);
			data.setContent(c.getContent());
			data.setEndDate(c.getEndDate());
			data.setHit(c.getHit());
			data.setName(c.getName());
			data.setSeq(c.getSeq());
			data.setShowOtherReportOfStu_state(c.getShowOtherReportOfStu_state());
			data.setStartDate(c.getStartDate());
			data.setSubmitOverDue_state(c.getSubmitOverDue_state());
			Km_reportResource km_reportResource = new Km_reportResource(data);
			km_reportResource.add(linkBuilder.slash(data.getSeq()).withRel("delete").withDeprecation("삭제"));
			km_reportResource.add(linkBuilder.slash(data.getSeq()).withRel("update").withDeprecation("수정"));
			resultList.add(km_reportResource);
		}
		result.put("list", resultList);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PostMapping("{classIdx}")
	public ResponseEntity save(@PathVariable long classIdx, @RequestBody @Valid KM_reportVO km_reportVO,
			Errors errors) {
		km_reportValidator.validate(km_reportVO, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		km_reportVO.setClassIdx(classIdx);
		KM_Report km_report = km_ReportService.save(km_reportVO);
		km_reportVO.setSeq(km_report.getSeq());
		Km_reportResource km_reportResource = new Km_reportResource(km_reportVO);
		km_reportResource.add(linkBuilder.slash(km_report.getSeq()).withRel("delete").withDeprecation("삭제"));
		km_reportResource.add(linkBuilder.slash(km_report.getSeq()).withRel("update").withDeprecation("수정"));
		return ResponseEntity.status(HttpStatus.OK).body(km_reportResource);
	}

	@PutMapping("{reportIdx}")
	public ResponseEntity update(@PathVariable long reportIdx, @RequestBody @Valid KM_reportVO km_reportVO,
			Errors errors) {
		km_reportValidator.validate(km_reportVO, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		km_ReportService.update(reportIdx, km_reportVO);
		return ResponseEntity.status(HttpStatus.OK).body(this.getReport(reportIdx));
	}

}
