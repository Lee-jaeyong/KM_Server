package ljy.book.admin.restAPI;

/**
 * 	@author 이재용
 *  1. TODO getReport	// 해당하는 과제를 가져오는 부분
 *  2. TODO getReportlist	// 과제 목록을 가져오는 부분
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ljy.book.admin.dto.Km_ReportDTO;
import ljy.book.admin.dto.validate.Km_reportValidator;
import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.resource.Km_reportResource;
import ljy.book.admin.repository.projection.Km_ReportFileProjection;
import ljy.book.admin.repository.projection.Km_ReportImgProjection;
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

	@GetMapping("{reportIdx}")
	public HashMap<String, Object> getReport(@PathVariable long reportIdx) {
		KM_Report km_report = new KM_Report();
		//km_report.setReportIdx(reportIdx);
		return km_ReportService.findByReportIdx(km_report);
	}

	@GetMapping("/search/{classIdx}")
	public HashMap<String, Object> searchKm_report(@PathVariable long classIdx, KM_Report km_report,
			Pageable pageable) {
		KM_class km_class = new KM_class();
		//km_class.setClassIdx(classIdx);
		return km_ReportService.searchKm_report(km_class, km_report, pageable);
	}

	@GetMapping(value = "/reportImg/{reportIdx}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Km_ReportImgProjection> getReportImgList(@PathVariable KM_Report reportIdx) {
		return km_ReportService.findByReportIdxAsImg(reportIdx);
	}

	@GetMapping(value = "/reportFile/{reportIdx}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Km_ReportFileProjection> getReportFileList(@PathVariable KM_Report reportIdx) {
		return km_ReportService.findByReportIdxAsFile(reportIdx);
	}

	@GetMapping(value = "/class/{classIdx}")
	public HashMap<String, Object> getReportlist(@PathVariable long classIdx, Pageable pageable) {
		KM_class km_class = new KM_class();
		//km_class.setClassIdx(classIdx);
		return km_ReportService.findByKmClass_ClassIdx(km_class, pageable);
	}

	@PostMapping
	public ResponseEntity addReport(@RequestBody @Valid Km_ReportDTO km_report, Errors errors) {
		Km_reportResource km_reportResource = new Km_reportResource(km_report);
		HashMap<String, Object> result = new HashMap<String, Object>();
		if (errors.hasErrors()) {
			result.put("errors", errors.getAllErrors());
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}

		km_reportValidator.validate(km_report, errors);

		if (errors.hasErrors()) {
			result.put("errors", errors.getAllErrors());
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
		return ResponseEntity.status(HttpStatus.OK).body(km_ReportService.save(km_report));
	}

	@PostMapping("fileupload/addImg/{reportIdx}")
	public ResponseEntity addReportImg(MultipartFile[] file, @PathVariable long reportIdx) {
		for (MultipartFile f : file) {
			km_fileUploadDownloadService.storeFile(f, "addReport_Img", reportIdx);
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("fileupload/addFile/{reportIdx}")
	public ResponseEntity addReportFile(MultipartFile[] file, @PathVariable long reportIdx) {
		for (MultipartFile f : file) {
			km_fileUploadDownloadService.storeFile(f, "addReport_File", reportIdx);
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping
	public HashMap<String, Object> updateReport(@RequestBody @Valid Km_ReportDTO km_reportDTO, Errors errors) {
		return km_ReportService.update(km_reportDTO);
	}

	@DeleteMapping
	public HashMap<String, String> deleteReport(@RequestBody Km_ReportDTO km_reportDTO) throws Exception {
		km_ReportService.delete(km_reportDTO);
		return null;
	}

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
