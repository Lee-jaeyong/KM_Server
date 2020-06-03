package ljy_yjw.team_manage.system.service.read.plan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ljy_yjw.team_manage.system.custom.util.CustomDate;
import ljy_yjw.team_manage.system.domain.dto.PlanByUserDTO;
import ljy_yjw.team_manage.system.domain.entity.PlanByUser;
import ljy_yjw.team_manage.system.domain.entity.Team;
import ljy_yjw.team_manage.system.domain.entity.TodoList;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.CanNotPerformException;
import ljy_yjw.team_manage.system.exception.exceptions.CheckInputValidException;
import ljy_yjw.team_manage.system.security.UsersService;

@Service
public class PlanExcelReadService {

	@Autowired
	UsersService userService;

	private void excelFileCheck(String fileExtension) throws CanNotPerformException {
		if (!fileExtension.equals("xlsx") && !fileExtension.equals("xls")) {
			throw new CanNotPerformException("엑셀 파일만 업로드 가능합니다.");
		}
	}

	private Workbook getWorkBook(String extension, MultipartFile file) throws IOException {
		if (extension.equals("xlsx"))
			return new XSSFWorkbook(file.getInputStream());
		return new HSSFWorkbook(file.getInputStream());
	}

	public List<PlanByUser> getDatas(Sheet worksheet, List<Users> userList)
		throws CheckInputValidException, CanNotPerformException, IOException {
		List<PlanByUser> result = new ArrayList<>();
		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			long seq = -1;
			String name = "";
			byte[] imgByte = null;
			Row row = worksheet.getRow(i);
			for (int j = 0; j < userList.size(); j++) {
				if (userList.get(j).getId().equals(row.getCell(0).getStringCellValue())) {
					seq = userList.get(j).getSeq();
					imgByte = userList.get(j).getImageByte(userService);
					name = userList.get(j).getName();
					break;
				}
			}
			if (seq == -1)
				throw new CanNotPerformException("[" + i + "] 열 " + row.getCell(0).getStringCellValue() + " 은 팀원이 아닙니다.");
			Users user = Users.builder().seq(seq).name(name).myImg(imgByte).id(row.getCell(0).getStringCellValue()).build();
			PlanByUserDTO chkPlan = PlanByUserDTO.builder().start(CustomDate.dateToLocalDate(row.getCell(2).getDateCellValue()))
				.end(CustomDate.dateToLocalDate(row.getCell(3).getDateCellValue())).build();
			chkPlan.isAfter("[" + i + "] 열 날짜 시작일은 종료일보다 작아야합니다.");
			PlanByUser plan = PlanByUser.builder().tag(row.getCell(1).getStringCellValue())
				.start(row.getCell(2).getDateCellValue()).end(row.getCell(3).getDateCellValue()).build();
			int cols = 4;
			List<TodoList> todoList = new ArrayList<TodoList>();
			while (true) {
				try {
					todoList.add(TodoList.builder().planByUser(plan).title(row.getCell(cols++).getStringCellValue()).build());
				} catch (Exception e) {
					plan.setUser(user);
					plan.setTodoList(todoList);
					result.add(plan);
					break;
				}
			}
		}
		return result;
	}

	public List<PlanByUser> excelDataRead(MultipartFile file, List<Users> users, Team team)
		throws CanNotPerformException, IOException, CheckInputValidException {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		this.excelFileCheck(extension);
		Workbook workbook = this.getWorkBook(extension, file);
		Sheet worksheet = workbook.getSheetAt(0);
		return getDatas(worksheet, users);
	}

}
