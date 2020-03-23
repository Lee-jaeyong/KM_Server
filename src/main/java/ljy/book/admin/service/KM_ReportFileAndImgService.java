package ljy.book.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ljy.book.admin.entity.KM_Report;
import ljy.book.admin.repository.projection.Km_ReportFileProjection;
import ljy.book.admin.repository.projection.Km_ReportImgProjection;

@Service
public class KM_ReportFileAndImgService {

//	@Autowired
//	KM_ReportFileAPI km_reportFileAPI;
//
//	@Autowired
//	KM_ReportImgAPI km_reportImgAPI;

	public List<Km_ReportImgProjection> findByKmReportImg_ReportIdx(KM_Report km_report) {
//		return km_reportImgAPI.findByKmReport_ReportIdx(km_report.getReportIdx());
		return null;
	}

	public List<Km_ReportFileProjection> findByKmReportFile_ReportIdx(KM_Report km_report) {
//		return km_reportFileAPI.findByKmReport_ReportIdx(km_report.getReportIdx());
		return null;
	}

//	public void save_File(Km_ReportFile km_reportFile) {
//		//km_reportFileAPI.save(km_reportFile);
//	}
//
//	public void save_Img(Km_ReportImg km_reportImg) {
//		//km_reportImgAPI.save(km_reportImg);
//	}

}
