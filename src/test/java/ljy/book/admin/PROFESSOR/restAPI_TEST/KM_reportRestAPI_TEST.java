package ljy.book.admin.PROFESSOR.restAPI_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.request.KM_reportVO;
import ljy.book.admin.security.KM_UserService;

public class KM_reportRestAPI_TEST extends CommonTestConfig {

	@Autowired
	KM_UserService km_userService;

	String accessToken_json;

	HashMap<String, Object> resultToken;

	String content;

	@Before
	public void init() throws Exception {
		this.login_access();
	}

	@Test
	@Ignore
	@Memo("토큰을 가진 사용자(교수)가 자신의 과제를 수정하는 경우")
	public void update() throws Exception {
		KM_reportVO km_reportVO = new KM_reportVO();
		km_reportVO.setName("C언어 레포fsdfsdfsdfds트는........");
		km_reportVO.setStartDate("2020-01-04");
		km_reportVO.setEndDate("2020-10-10");
		km_reportVO.setUseSubmitDates(BooleanState.YSE);

		String _content = this.requestBodyPlus(objMapper.writeValueAsString(km_reportVO), content);

		this.mvc
			.perform(RestDocumentationRequestBuilders.put("/api/professor/report/{seq}", 5).content(_content)
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print()).andExpect(status().isOk()).andDo(
				document("Update KM_report By Professor", pathParameters(parameterWithName("seq").description("수업 번호")),
					requestFields(fieldWithPath("Authorization").type(JsonFieldType.STRING).description("보안 토큰").optional(),
						fieldWithPath("name").type(JsonFieldType.STRING).description("과제명").optional(),
						fieldWithPath("startDate").type(JsonFieldType.STRING).description("과제 시작일").optional(),
						fieldWithPath("endDate").type(JsonFieldType.STRING).description("과제 마감일").optional(),
						fieldWithPath("content").type(JsonFieldType.STRING).description("과제 내용").optional(),
						fieldWithPath("submitOverDue_state").type(JsonFieldType.STRING).description("마감일 이후 제출 여부").optional(),
						fieldWithPath("showOtherReportOfStu_state").type(JsonFieldType.STRING).description("제출 과제 공개 여부")
							.optional(),
						fieldWithPath("classIdx").type(Integer.class).description("해당 수업 번호").optional(),
						fieldWithPath("seq").type(Integer.class).description("과제 번호").optional(),
						fieldWithPath("hit").type(Integer.class).description("조회수").optional(),
						fieldWithPath("fileList").type(String.class).description("해당 과제 파일 리스트").optional(),
						fieldWithPath("imgList").type(String.class).description("해당 과제 이미지 리스트").optional()),
					responseFields(fieldWithPath("classIdx").type(Integer.class).description("해당 수업 번호").optional(),
						fieldWithPath("seq").type(Integer.class).description("과제 번호").optional(),
						fieldWithPath("name").type(String.class).description("과제명").optional(),
						fieldWithPath("startDate").type(String.class).description("과제 시작일").optional(),
						fieldWithPath("endDate").type(String.class).description("과제 마감일").optional(),
						fieldWithPath("content").type(String.class).description("과제 내용").optional(),
						fieldWithPath("hit").type(Integer.class).description("조회수").optional(),
						fieldWithPath("submitOverDue_state").type(String.class).description("마감 이후 제출 여부").optional(),
						fieldWithPath("showOtherReportOfStu_state").type(String.class).description("제출 과제 공개 여부").optional(),
						fieldWithPath("fileList").type(String.class).description("해당 과제 파일 리스트").optional(),
						fieldWithPath("imgList").type(String.class).description("해당 과제 이미지 리스트").optional(),
						fieldWithPath("_links.self.href").type(String.class).description("").optional(),
						fieldWithPath("_links.delete.href").type(String.class).description("수정").optional(),
						fieldWithPath("_links.update.href").type(String.class).description("삭제").optional(),
						fieldWithPath("_links.profile.href").type(String.class).description("참고").optional())));
	}

	@Test
	@Ignore
	@Memo("토큰을 가진 사용자(교수)가 자신의 수업에 과제를 등록하는 경우")
	public void saveReport() throws Exception {
		KM_reportVO km_reportVO = new KM_reportVO();
		km_reportVO.setName("C언어 레포트는........");
		km_reportVO.setStartDate("2020-01-04");
		km_reportVO.setEndDate("2020-10-10");
		km_reportVO.setUseSubmitDates(BooleanState.YSE);

		String _content = this.requestBodyPlus(objMapper.writeValueAsString(km_reportVO), content);

		this.mvc
			.perform(RestDocumentationRequestBuilders.post("/api/professor/report/{seq}", 3).content(_content)
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print()).andExpect(status().isOk()).andDo(
				document("Save KM_report By Professor", pathParameters(parameterWithName("seq").description("과제 번호")),
					requestFields(fieldWithPath("Authorization").type(JsonFieldType.STRING).description("보안 토큰").optional(),
						fieldWithPath("name").type(JsonFieldType.STRING).description("과제명").optional(),
						fieldWithPath("startDate").type(JsonFieldType.STRING).description("과제 시작일").optional(),
						fieldWithPath("endDate").type(JsonFieldType.STRING).description("과제 마감일").optional(),
						fieldWithPath("content").type(JsonFieldType.STRING).description("과제 내용").optional(),
						fieldWithPath("submitOverDue_state").type(JsonFieldType.STRING).description("마감일 이후 제출 여부").optional(),
						fieldWithPath("showOtherReportOfStu_state").type(JsonFieldType.STRING).description("제출 과제 공개 여부")
							.optional(),
						fieldWithPath("classIdx").type(Integer.class).description("해당 수업 번호").optional(),
						fieldWithPath("seq").type(Integer.class).description("과제 번호").optional(),
						fieldWithPath("hit").type(Integer.class).description("조회수").optional(),
						fieldWithPath("fileList").type(String.class).description("해당 과제 파일 리스트").optional(),
						fieldWithPath("imgList").type(String.class).description("해당 과제 이미지 리스트").optional()),
					responseFields(fieldWithPath("classIdx").type(Integer.class).description("해당 수업 번호").optional(),
						fieldWithPath("seq").type(Integer.class).description("과제 번호").optional(),
						fieldWithPath("name").type(String.class).description("과제명").optional(),
						fieldWithPath("startDate").type(String.class).description("과제 시작일").optional(),
						fieldWithPath("endDate").type(String.class).description("과제 마감일").optional(),
						fieldWithPath("content").type(String.class).description("과제 내용").optional(),
						fieldWithPath("hit").type(Integer.class).description("조회수").optional(),
						fieldWithPath("submitOverDue_state").type(String.class).description("마감 이후 제출 여부").optional(),
						fieldWithPath("showOtherReportOfStu_state").type(String.class).description("제출 과제 공개 여부").optional(),
						fieldWithPath("fileList").type(String.class).description("해당 과제 파일 리스트").optional(),
						fieldWithPath("imgList").type(String.class).description("해당 과제 이미지 리스트").optional(),
						fieldWithPath("_links.self.href").type(String.class).description("").optional(),
						fieldWithPath("_links.delete.href").type(String.class).description("수정").optional(),
						fieldWithPath("_links.update.href").type(String.class).description("삭제").optional(),
						fieldWithPath("_links.profile.href").type(String.class).description("참고").optional())));
		;
	}

	@Test
	@Ignore
	@Memo("토큰을 가진 사용자(교수)가 다른 과제에 대한 파일 리스트를 불러올 경우")
	public void getFileList_Faild() throws Exception {
		this.mvc.perform(RestDocumentationRequestBuilders.get("/api/professor/report/{seq}/fileList", 2).content(content))
			.andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Ignore
	@Memo("토큰을 가진 사용자(교수)가 자신의 과제에 대한 파일 리스트를 불러올 경우")
	public void getFileList() throws Exception {
		this.mvc.perform(RestDocumentationRequestBuilders.get("/professor/report/{seq}/fileList", 5).content(content))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("Access KM_fileAndImgList of KM_report by Professor",
				pathParameters(parameterWithName("seq").description("과제 번호")),
				requestFields(fieldWithPath("Authorization").description("보안 토큰").optional()),
				responseFields(fieldWithPath("_links.profile.rel").type(String.class).description("").optional(),
					fieldWithPath("_links.profile.href").type(String.class).description("참고").optional(),
					fieldWithPath("reportFileAndImgList[].fileName").type(String.class).description("파일명").optional(),
					fieldWithPath("reportFileAndImgList[].type").type(String.class).description("파일 타입").optional())));
	}

	@Test
	@Ignore
	@Memo("토큰을 가진 사용자(교수)가 다른 클래스의 과제 정보를 접근할 경우")
	public void getReportInfo_Faild() throws Exception {
		this.mvc.perform(RestDocumentationRequestBuilders.get("/api/professor/report/{seq}", 1).content(content)).andDo(print())
			.andExpect(status().isNotFound());
	}

	@Test
	@Ignore
	@Memo("토큰을 가진 사용자(교수)가 해당 클래스의 레포트 정보를 가져옴")
	public void getReportInfo() throws Exception {
		this.mvc.perform(RestDocumentationRequestBuilders.get("/api/professor/report/{seq}", 5).content(content)).andDo(print())
			.andExpect(status().isOk())
			.andDo(document("Access Km_report Info by Professor", pathParameters(parameterWithName("seq").description("수업 번호")),
				requestFields(fieldWithPath("Authorization").description("보안 토큰").optional()),
				responseFields(fieldWithPath("classIdx").type(Integer.class).description("해당 수업 번호").optional(),
					fieldWithPath("seq").type(Integer.class).description("과제 번호").optional(),
					fieldWithPath("name").type(String.class).description("과제명").optional(),
					fieldWithPath("startDate").type(String.class).description("과제 시작일").optional(),
					fieldWithPath("endDate").type(String.class).description("과제 마감일").optional(),
					fieldWithPath("content").type(String.class).description("과제 내용").optional(),
					fieldWithPath("hit").type(Integer.class).description("조회수").optional(),
					fieldWithPath("submitOverDue_state").type(String.class).description("마감 이후 제출 여부").optional(),
					fieldWithPath("showOtherReportOfStu_state").type(String.class).description("제출 과제 공개 여부").optional(),
					fieldWithPath("fileList").type(String.class).description("해당 과제 파일 리스트").optional(),
					fieldWithPath("imgList").type(String.class).description("해당 과제 이미지 리스트").optional(),
					fieldWithPath("_links.self.href").type(String.class).description("").optional(),
					fieldWithPath("_links.delete.href").type(String.class).description("수정").optional(),
					fieldWithPath("_links.update.href").type(String.class).description("삭제").optional(),
					fieldWithPath("_links.profile.href").type(String.class).description("참고").optional())));
	}

	@Test
	@Ignore
	@Memo("토큰을 가진 사용자(교수)가 해당 클래스의 레포트 리스트를 가져옴")
	public void getReportList() throws Exception {
		this.mvc
			.perform(RestDocumentationRequestBuilders
				.get("/api/professor/report/class/{seq}/list", 3).param("size", "10").param("page", "0").param("name", "")
				.param("searchType", "").param("startDate", "").param("endDate", "").content(content))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("Access KM_report by Professor", pathParameters(parameterWithName("seq").description("수업 번호")),
				requestFields(fieldWithPath("Authorization").description("보안 토큰").optional(),
					fieldWithPath("size").type(String.class).description("보여줄 과제 개수").optional(),
					fieldWithPath("page").type(String.class).description("현재 과제 페이지").optional(),
					fieldWithPath("name").type(String.class).description("검색 과제명").optional(),
					fieldWithPath("searchType").type(String.class).description("검색 타입").optional(),
					fieldWithPath("startDate").type(String.class).description("검색 과제 시작일").optional(),
					fieldWithPath("endDate").type(String.class).description("검색 과제 마감일").optional()),
				responseFields(fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("총 과제 개수").optional(),
					fieldWithPath("list[].classIdx").type(JsonFieldType.NUMBER).description("해당 수업 번호").optional(),
					fieldWithPath("list[].seq").type(JsonFieldType.NUMBER).description("과제 번호").optional(),
					fieldWithPath("list[].name").type(JsonFieldType.STRING).description("과제명").optional(),
					fieldWithPath("list[].startDate").type(JsonFieldType.STRING).description("과제 시작일").optional(),
					fieldWithPath("list[].endDate").type(JsonFieldType.STRING).description("과제 마감일").optional(),
					fieldWithPath("list[].content").type(JsonFieldType.STRING).description("과제 내용").optional(),
					fieldWithPath("list[].hit").type(JsonFieldType.NUMBER).description("조회수").optional(),
					fieldWithPath("list[].submitOverDue_state").type(JsonFieldType.STRING).description("마감 이후 제출 가능 여부")
						.optional(),
					fieldWithPath("list[].showOtherReportOfStu_state").type(JsonFieldType.STRING).description("제출 과제 공개 여부")
						.optional(),
					fieldWithPath("list[].fileList").type(JsonFieldType.STRING).description("과제 파일 목록").optional(),
					fieldWithPath("list[].imgList").type(JsonFieldType.STRING).description("과제 이미지 목록").optional(),
					fieldWithPath("list[].links[].rel").type(JsonFieldType.STRING).description("").optional(),
					fieldWithPath("list[].links[].href").type(JsonFieldType.STRING).description("").optional())));
	}

	private void login_access() throws Exception {
		accessToken_json = this.mvc
			.perform(RestDocumentationRequestBuilders.post("/oauth/token").with(httpBasic(clientId, clientPass))
				.param("username", "dlwodyd202").param("password", "dlwodyd").param("grant_type", "password"))
			.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		resultToken = objMapper.readValue(accessToken_json, HashMap.class);

		content = "{\"Authorization\" : \"Bearer " + resultToken.get("access_token").toString() + "\"}";
	}
}
