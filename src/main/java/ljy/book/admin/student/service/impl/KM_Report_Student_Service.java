package ljy.book.admin.student.service.impl;

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
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.FileType;
import ljy.book.admin.jpaAPI.KM_ReportAPI;
import ljy.book.admin.jpaAPI.KM_fileAndImgOfReportAPI;
import ljy.book.admin.request.KM_reportVO;
import ljy.book.admin.service.KM_reportServiceList;

@Service
@Transactional
public class KM_Report_Student_Service implements KM_reportServiceList {

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
		KM_Report findReport = km_ReportAPI.findBySeqAndKmClass_KmSignUpClassForStu_SignUpStateAndKmClass_KmUser_Id(reportIdx,
			BooleanState.YSE, id);
		result.setContent(findReport.getContent());
		result.setEndDate(findReport.getEndDate());
		result.setHit(findReport.getHit());
		result.setName(findReport.getName());
		result.setSeq(findReport.getSeq());
		result.setStartDate(findReport.getStartDate());
		result.setUseSubmitDates(findReport.getSubmitOverDue_state());
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
	 * 학생 권한 메소드
	 */
}
