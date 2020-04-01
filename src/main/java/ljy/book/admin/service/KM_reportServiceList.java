package ljy.book.admin.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import ljy.book.admin.common.object.CustomSearchObject;
import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.request.KM_reportVO;

public interface KM_reportServiceList {
	public boolean checkBySeqAndUserId(long reportIdx, String id);

	public List<Object> getReportFileList(long reportIdx, String id);

	public KM_reportVO getReport(long reportIdx, String id);

	public long getTotalCount(long classIdx, CustomSearchObject customSearchObj, String id);

	public List<KM_Report> getReportList(long classIdx, Pageable pageable, CustomSearchObject customSearchObj, String id);
}
