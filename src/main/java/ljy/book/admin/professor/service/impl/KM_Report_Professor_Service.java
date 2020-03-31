package ljy.book.admin.professor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ljy.book.admin.common.object.CustomSearchObject;
import ljy.book.admin.customRepository.mybaits.Km_reportDAO;
import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.KM_fileAndImgOfReport;
import ljy.book.admin.entity.enums.FileType;
import ljy.book.admin.jpaAPI.KM_ReportAPI;
import ljy.book.admin.jpaAPI.KM_fileAndImgOfReportAPI;
import ljy.book.admin.request.KM_reportVO;
import ljy.book.admin.service.KM_reportServiceList;

@Service
@Transactional
public class KM_Report_Professor_Service implements KM_reportServiceList {

	@Autowired
	KM_ReportAPI km_ReportAPI;

	@Autowired
	Km_reportDAO km_reportDAO;

	@Autowired
	KM_fileAndImgOfReportAPI km_fileAndImgOfReportAPI;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public boolean checkBySeqAndUserId(long reportIdx, String id) {
		if (km_ReportAPI.findByKmClass_KmUser_IdAndSeq(id, reportIdx) == null)
			return false;
		return true;
	}

	@Override
	public List<Object> getReportFileList(long reportIdx, String id) {
		List<Object> fileList = new ArrayList<Object>();
		for (KM_fileAndImgOfReport c : km_fileAndImgOfReportAPI.findByKmReport_seqAndKmReport_KmClass_KmUser_Id(reportIdx, id)) {
			HashMap<String, String> result = new HashMap<String, String>();
			result.put("fileName", c.getFileName());
			result.put("type", c.getType().toString());
			fileList.add(result);
		}
		return fileList;
	}

	@Override
	public KM_reportVO getReport(long reportIdx, String id) {
		KM_reportVO result = new KM_reportVO();
		KM_Report findReport = km_ReportAPI.findByKmClass_KmUser_IdAndSeq(id, reportIdx);
		result.setContent(findReport.getContent());
		result.setEndDate(findReport.getEndDate());
		result.setHit(findReport.getHit());
		result.setName(findReport.getName());
		result.setSeq(findReport.getSeq());
		result.setShowOtherReportOfStu_state(findReport.getShowOtherReportOfStu_state());
		result.setStartDate(findReport.getStartDate());
		result.setSubmitOverDue_state(findReport.getSubmitOverDue_state());
		if (findReport != null) {
			List<KM_fileAndImgOfReport> fileList = km_fileAndImgOfReportAPI
				.findByKmReport_seqAndKmReport_KmClass_KmUser_Id(reportIdx, id);
			StringBuilder _fileList = new StringBuilder();
			StringBuilder _imgList = new StringBuilder();
			for (KM_fileAndImgOfReport c : fileList) {
				if (c.getType() == FileType.FILE)
					_fileList.append(c.getFileName() + ",");
				else
					_imgList.append(c.getFileName() + ",");
			}
			result.setFileList(_fileList.toString());
			result.setImgList(_imgList.toString());
		}
		return result;
	}

	@Override
	public long getTotalCount(long classIdx, CustomSearchObject customSearchObj, String id) {
		return km_ReportAPI.countSearch_Km_report(classIdx, customSearchObj, id);
	}

	@Override
	public List<KM_Report> getReportList(long classIdx, Pageable pageable, CustomSearchObject customSearchObj, String id) {
		return km_ReportAPI.search_Km_report(classIdx, pageable, customSearchObj, id);
	}

	
	/*
	 * 교수 권한 메소드
	 */
	
	
	public KM_Report save(KM_reportVO km_reportVO) {
		KM_class km_class = new KM_class();
		km_class.setSeq(km_reportVO.getClassIdx());
		KM_Report km_report = modelMapper.map(km_reportVO, KM_Report.class);
		km_class.addKmReport(km_report);
		return km_ReportAPI.save(km_report);
	}

	public boolean uploadFile(String fileName, FileType fileType, long idx) {
		KM_Report km_report = new KM_Report();
		km_report.setSeq(idx);
		KM_fileAndImgOfReport km_fileAndImgOfReport = new KM_fileAndImgOfReport();
		km_fileAndImgOfReport.setFileName(fileName);
		km_fileAndImgOfReport.setType(fileType);
		km_report.addKmFileAndImgOfReport(km_fileAndImgOfReport);
		km_fileAndImgOfReportAPI.save(km_fileAndImgOfReport);
		return true;
	}

	public boolean update(long reportIdx, KM_reportVO km_reportVO) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", km_reportVO.getName());
		map.put("startDate", km_reportVO.getStartDate());
		map.put("endDate", km_reportVO.getEndDate());
		map.put("content", km_reportVO.getContent());
		map.put("submitOverDue_state", km_reportVO.getSubmitOverDue_state().toString());
		map.put("showOtherReportOfStu_state", km_reportVO.getShowOtherReportOfStu_state().toString());
		map.put("seq", Long.toString(reportIdx));
		km_reportDAO.update(map);
		return true;
	}

	public boolean deleteFile(long idx, String fileName) {
		km_fileAndImgOfReportAPI.deleteByKmReport_seqAndFileName(idx, fileName);
		return true;
	}

//	@Autowired
//	public KM_ReportService(CustomFileUpload prop) {
//		this.fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
//	}

//	public Optional<KM_Report> checkByReportIdx(KM_Report km_report) {
//		//return km_ReportAPI.findById(km_report.getReportIdx());
//		return null;
//	}
//
//	public HashMap<String, Object> searchKm_report(KM_class km_class, KM_Report km_report, Pageable pageable) {
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		List<KM_Report> before_result_list = km_ReportAPI.search_Km_report(km_class, km_report, pageable);
//		List<Km_ReportDTO> after_result_list = new ArrayList<Km_ReportDTO>();
//		for (KM_Report c : before_result_list) {
//			after_result_list.add(modelMapper.map(c, Km_ReportDTO.class));
//		}
//		long searchCount = km_ReportAPI.countSearch_Km_report(km_class, km_report);
//		result.put("result", after_result_list);
//		result.put("report_count", searchCount);
//		//result.put("blockInfo", customBlock.get().getBlockInfo(searchCount, pageable.getPageNumber()));
//		return result;
//	}
//
//	public List<Km_ReportFileProjection> findByReportIdxAsFile(KM_Report km_report) {
//		return km_reportFileAndImgService.findByKmReportFile_ReportIdx(km_report);
//	}
//
//	public List<Km_ReportImgProjection> findByReportIdxAsImg(KM_Report km_report) {
//		return km_reportFileAndImgService.findByKmReportImg_ReportIdx(km_report);
//	}
//
//	public HashMap<String, Object> findByReportIdx(KM_Report km_report) {
//		HashMap<String, Object> resultMap = new HashMap<String, Object>();
//		//resultMap.put("report_info", km_ReportAPI.findByReportIdx(km_report.getReportIdx()));
//		resultMap.put("report_Img", km_reportFileAndImgService.findByKmReportImg_ReportIdx(km_report));
//		resultMap.put("report_File", km_reportFileAndImgService.findByKmReportFile_ReportIdx(km_report));
//		return resultMap;
//	}
//
//	public HashMap<String, Object> findByKmClass_ClassIdx(KM_class km_class, Pageable pageable) {
//		HashMap<String, Object> resultMap = new HashMap<String, Object>();
//		//resultMap.put("result",
//		//		km_ReportAPI.findByKmClass_ClassIdxOrderByReportIdxDesc(km_class.getClassIdx(), pageable));
////		resultMap.put("blockInfo", customBlock.get()
////				.getBlockInfo(km_ReportAPI.countByKmClass_ClassIdx(km_class.getClassIdx()), pageable.getPageNumber()));
//		return resultMap;
//	}
//
//	public Km_ReportDTO save(Km_ReportDTO km_report) {
//		KM_class save_km_class = new KM_class();
//		//save_km_class.setClassIdx(km_report.getClassIdx());
//		KM_Report save_km_report = modelMapper.map(km_report, KM_Report.class);
//		return modelMapper.map(km_ReportAPI.save(save_km_report), Km_ReportDTO.class);
//	}
//
//	public HashMap<String, Object> update(Km_ReportDTO km_report) {
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		//km_ReportAPI.update(km_report.getReportTitle(), km_report.getReportContent(), km_report.getReportStartDate(),
//		//		km_report.getReportEndDate(), km_report.getReportIdx());
//		result.put("result", true);
//		return result;
//	}
//
//	public boolean delete(Km_ReportDTO km_reportDTO) throws Exception {
//		//km_ReportAPI.deleteByReportIdx(km_reportDTO.getReportIdx());
//		// 파일 전체 삭제
//		String resultfilePath = "downloadList/report/reportImg/" + km_reportDTO.getReportIdx() + "/";
//		Path filePath = this.fileLocation.resolve(resultfilePath).normalize();
//		Resource resource = new UrlResource(filePath.toUri());
//		File deleteFile = resource.getFile();
//		if (deleteFile.isDirectory()) {
//			File[] folder_list = deleteFile.listFiles(); // 파일리스트 얻어오기
//			for (int j = 0; j < folder_list.length; j++) {
//				folder_list[j].delete(); // 파일 삭제
//			}
//		}
//		deleteFile.delete(); // 대상폴더 삭제
//
//		resultfilePath = "downloadList/report/reportFile/" + km_reportDTO.getReportIdx() + "/";
//		filePath = this.fileLocation.resolve(resultfilePath).normalize();
//		resource = new UrlResource(filePath.toUri());
//		deleteFile = resource.getFile();
//		if (deleteFile.isDirectory()) {
//			File[] folder_list = deleteFile.listFiles(); // 파일리스트 얻어오기
//			for (int j = 0; j < folder_list.length; j++) {
//				folder_list[j].delete(); // 파일 삭제
//			}
//		}
//		deleteFile.delete(); // 대상폴더 삭제
//		return true;
//	}
}
