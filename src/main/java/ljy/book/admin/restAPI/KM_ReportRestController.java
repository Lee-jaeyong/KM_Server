package ljy.book.admin.restAPI;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("{classIdx}/list")
	public ResponseEntity getReportList(@PathVariable long classIdx, Pageable pageable) {
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(this.getClass());
		List<Object> result = new ArrayList<Object>();
		for (KM_Report c : km_ReportService.getReportList(classIdx, pageable)) {
			KM_reportVO data = modelMapper.map(c, KM_reportVO.class);
			Km_reportResource km_reportResource = new Km_reportResource(data);
			km_reportResource.add(linkBuilder.slash(data.getSeq()).withRel("delete").withDeprecation("삭제"));
			km_reportResource.add(linkBuilder.slash(data.getSeq()).withRel("update").withDeprecation("수정"));
			result.add(km_reportResource);
		}
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

//	@GetMapping("{reportIdx}")
//	public HashMap<String, Object> getReport(@PathVariable long reportIdx) {
//		KM_Report km_report = new KM_Report();
//		//km_report.setReportIdx(reportIdx);
//		return km_ReportService.findByReportIdx(km_report);
//	}
//
//	@GetMapping("/search/{classIdx}")
//	public HashMap<String, Object> searchKm_report(@PathVariable long classIdx, KM_Report km_report,
//			Pageable pageable) {
//		KM_class km_class = new KM_class();
//		//km_class.setClassIdx(classIdx);
//		return km_ReportService.searchKm_report(km_class, km_report, pageable);
//	}
//
//	@GetMapping(value = "/reportImg/{reportIdx}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<Km_ReportImgProjection> getReportImgList(@PathVariable KM_Report reportIdx) {
//		return km_ReportService.findByReportIdxAsImg(reportIdx);
//	}
//
//	@GetMapping(value = "/reportFile/{reportIdx}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<Km_ReportFileProjection> getReportFileList(@PathVariable KM_Report reportIdx) {
//		return km_ReportService.findByReportIdxAsFile(reportIdx);
//	}
//
//	@GetMapping(value = "/class/{classIdx}")
//	public HashMap<String, Object> getReportlist(@PathVariable long classIdx, Pageable pageable) {
//		KM_class km_class = new KM_class();
//		//km_class.setClassIdx(classIdx);
//		return km_ReportService.findByKmClass_ClassIdx(km_class, pageable);
//	}
//
//	@PostMapping
//	public ResponseEntity addReport(@RequestBody @Valid Km_ReportDTO km_report, Errors errors) {
//		Km_reportResource km_reportResource = new Km_reportResource(km_report);
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		if (errors.hasErrors()) {
//			result.put("errors", errors.getAllErrors());
//			return ResponseEntity.status(HttpStatus.OK).body(result);
//		}
//
//		km_reportValidator.validate(km_report, errors);
//
//		if (errors.hasErrors()) {
//			result.put("errors", errors.getAllErrors());
//			return ResponseEntity.status(HttpStatus.OK).body(result);
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(km_ReportService.save(km_report));
//	}
//
//	@PostMapping("fileupload/addImg/{reportIdx}")
//	public ResponseEntity addReportImg(MultipartFile[] file, @PathVariable long reportIdx) {
//		for (MultipartFile f : file) {
//			km_fileUploadDownloadService.storeFile(f, "addReport_Img", reportIdx);
//		}
//		return ResponseEntity.status(HttpStatus.OK).build();
//	}
//
//	@PostMapping("fileupload/addFile/{reportIdx}")
//	public ResponseEntity addReportFile(MultipartFile[] file, @PathVariable long reportIdx) {
//		for (MultipartFile f : file) {
//			km_fileUploadDownloadService.storeFile(f, "addReport_File", reportIdx);
//		}
//		return ResponseEntity.status(HttpStatus.OK).build();
//	}
//
//	@PutMapping
//	public HashMap<String, Object> updateReport(@RequestBody @Valid Km_ReportDTO km_reportDTO, Errors errors) {
//		return km_ReportService.update(km_reportDTO);
//	}
//
//	@DeleteMapping
//	public HashMap<String, String> deleteReport(@RequestBody Km_ReportDTO km_reportDTO) throws Exception {
//		km_ReportService.delete(km_reportDTO);
//		return null;
//	}
}
