package ljy.book.admin.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ljy.book.admin.entity.KM_Report;

@Component
public class Km_ReportConverter implements Formatter<KM_Report> {

	@Override
	public String print(KM_Report object, Locale locale) {
//		return object.getReportIdx().toString();
		return null;
	}

	@Override
	public KM_Report parse(String text, Locale locale) throws ParseException {
//		KM_Report km_report = new KM_Report();
//		km_report.setReportIdx(Long.parseLong(text));
//		return km_report;
		return null;
	}

}
