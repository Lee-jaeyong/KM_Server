package ljy.book.admin.repository.projection;

public interface Km_ReportProjection {
	Long getReportIdx();

	String getReportTitle();

	String getReportContent();

	int getReportHit();

	String getReportStartDate();

	String getReportEndDate();
}
