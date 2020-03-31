package ljy.book.admin.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import ljy.book.admin.common.object.CustomSearchObject;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.request.KM_reportVO;

public interface KM_reportServiceList {

	@Memo("해당 사용자에게 해당 과제에 대한 권한이 있는지를 확인하는 메소드")
	public boolean checkBySeqAndUserId(long reportIdx, String id);

	@Memo("해당 사용자에게 해당 과제에 대한 권한이 있으며 있다면 과제 파일 리스트를 가져오는 메소드")
	public List<Object> getReportFileList(long reportIdx, String id);

	@Memo("해당 과제의 정보를 가져오는 메소드")
	public KM_reportVO getReport(long reportIdx, String id);

	@Memo("과제의 총 갯수를 가져오는 메소드")
	public long getTotalCount(long classIdx, CustomSearchObject customSearchObj, String id);

	@Memo("과제의 리스트를 가져오는 메소드")
	public List<KM_Report> getReportList(long classIdx, Pageable pageable, CustomSearchObject customSearchObj, String id);
}
