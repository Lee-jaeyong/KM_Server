package ljy.book.admin.PROFESSOR.restAPI_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.enums.BooleanState;
import ljy.book.admin.entity.enums.ClassType;
import ljy.book.admin.entity.enums.SaveState;
import ljy.book.admin.request.KM_classVO;
import ljy.book.admin.security.KM_UserService;

public class KM_classRestAPI_TEST extends CommonTestConfig {

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
	@Memo("해당 수업 승인요청을 수락하는 경우")
	public void signUpClassForStuSuccess() throws Exception {
		this.mvc
			.perform(
				RestDocumentationRequestBuilders.put("/api/professor/class/{seq}/signUpList/{signUpIdx}", 3, 1).content(content))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("Update signUpState of SignUpClassForStu By Professor",
				pathParameters(parameterWithName("seq").description("수업 번호"),
					parameterWithName("signUpIdx").description("승인 요청 번호")),
				requestFields(fieldWithPath("Authorization").type(JsonFieldType.STRING).description("보안 토큰").optional())));
	}

	@Test
	@Ignore
	@Memo("해당 수업 승인요청 리스트를 가져오는 경우")
	public void signUpClassForStu() throws Exception {
		this.mvc.perform(RestDocumentationRequestBuilders.get("/api/professor/class/{seq}/signUpList", 3).content(content))
			.andDo(print()).andExpect(status().isOk()).andDo(
				document("Access SignUpClassForStu By Professor", pathParameters(parameterWithName("seq").description("수업 번호")),
					requestFields(fieldWithPath("Authorization").type(JsonFieldType.STRING).description("보안 토큰").optional()),
					responseFields(fieldWithPath("_embedded[].seq").type(JsonFieldType.NUMBER).description("수업 번호").optional(),
						fieldWithPath("_embedded[].signUp_state").type(JsonFieldType.BOOLEAN).description("요청상태").optional(),
						fieldWithPath("_embedded[].date").type(JsonFieldType.STRING).description("요청일").optional(),
						fieldWithPath("_embedded[].userId").type(JsonFieldType.STRING).description("요청 학생"),
						fieldWithPath("_links.profile.rel").type(JsonFieldType.STRING).description(""),
						fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description(""),
						fieldWithPath("_links.self.rel").type(JsonFieldType.STRING).description(""),
						fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description(""))));
	}

	@Test
	@Ignore
	@Memo("토큰을 가진 사용자(교수)가 클래스 정보를 수정")
	public void updateKm_class() throws Exception {
		KM_classVO km_classVO = new KM_classVO();
		km_classVO.setSeq(3l);
		km_classVO.setName("RESTAPI");
		km_classVO.setStartDate("2020-03-01");
		km_classVO.setEndDate("2020-10-01");
		km_classVO.setContent("fdsfds");
		km_classVO.setPlannerDocName("dlwodyd202.xlsx");
		km_classVO.setType(ClassType.MAJOR);
		km_classVO.setReplyPermit_state(BooleanState.YSE);
		km_classVO.setSelectMenu("REPORT,");
		km_classVO.setUse_state(BooleanState.YSE);
		km_classVO.setSaveState(SaveState.SAVE);

		String _content = this.requestBodyPlus(objMapper.writeValueAsString(km_classVO), content);

		this.mvc
			.perform(RestDocumentationRequestBuilders
				.put("/api/professor/class").content(_content).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andDo(print())
			.andDo(document("Update KM_class By Professor",
				requestFields(fieldWithPath("Authorization").type(JsonFieldType.STRING).description("보안 토큰").optional(),
					fieldWithPath("seq").type(JsonFieldType.NUMBER).description("수업 번호").optional(),
					fieldWithPath("name").type(JsonFieldType.STRING).description("수업명"),
					fieldWithPath("startDate").type(JsonFieldType.STRING).description("수업 시작일"),
					fieldWithPath("endDate").type(JsonFieldType.STRING).description("수업 종료일"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("수업 내용").optional(),
					fieldWithPath("plannerDocName").type(JsonFieldType.STRING).description("강의 계획서").optional(),
					fieldWithPath("type").type(JsonFieldType.STRING).description("수업 종류").optional(),
					fieldWithPath("replyPermit_state").type(JsonFieldType.STRING).description("댓글 허용 여부").optional(),
					fieldWithPath("selectMenu").type(JsonFieldType.STRING).description("연관 메뉴").optional(),
					fieldWithPath("use_state").type(JsonFieldType.STRING).description("사용 여부").optional(),
					fieldWithPath("saveState").type(JsonFieldType.STRING).description("임시저장 여부").optional(),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("").optional(),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("참고").optional(),
					fieldWithPath("_links.delete.href").type(JsonFieldType.STRING).description("수업 삭제").optional(),
					fieldWithPath("_links.update.href").type(JsonFieldType.STRING).description("수업 수정").optional()),
				responseFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("수업 번호").optional(),
					fieldWithPath("name").type(JsonFieldType.STRING).description("수업명").optional(),
					fieldWithPath("startDate").type(JsonFieldType.STRING).description("수업 시작일"),
					fieldWithPath("endDate").type(JsonFieldType.STRING).description("수업 종료일"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("수업 내용").optional(),
					fieldWithPath("plannerDocName").type(JsonFieldType.STRING).description("강의 계획서").optional(),
					fieldWithPath("type").type(JsonFieldType.STRING).description("수업 종류").optional(),
					fieldWithPath("replyPermit_state").type(JsonFieldType.STRING).description("댓글 허용 여부").optional(),
					fieldWithPath("selectMenu").type(JsonFieldType.STRING).description("연관 메뉴").optional(),
					fieldWithPath("use_state").type(JsonFieldType.STRING).description("사용 여부").optional(),
					fieldWithPath("saveState").type(JsonFieldType.STRING).description("임시저장 여부").optional(),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("").optional(),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("참고").optional(),
					fieldWithPath("_links.delete.href").type(JsonFieldType.STRING).description("수업 삭제").optional(),
					fieldWithPath("_links.update.href").type(JsonFieldType.STRING).description("수업 수정").optional())));
	}

	@Test
	@Ignore
	@Memo("토큰을 가진 사용자(교수)가 클래스 정보를 등록")
	public void saveKm_class() throws Exception {
		KM_classVO km_classVO = new KM_classVO();
		km_classVO.setName("RESTAPI");
		km_classVO.setStartDate("2020-03-01");
		km_classVO.setEndDate("2020-10-01");
		km_classVO.setContent("fdsfds");
		km_classVO.setPlannerDocName("dlwodyd202.xlsx");
		km_classVO.setType(ClassType.MAJOR);
		km_classVO.setReplyPermit_state(BooleanState.YSE);
		km_classVO.setSelectMenu("REPORT,");
		km_classVO.setUse_state(BooleanState.YSE);
		km_classVO.setSaveState(SaveState.SAVE);

		String _content = this.requestBodyPlus(objMapper.writeValueAsString(km_classVO), content);

		// When
		this.mvc
			.perform(RestDocumentationRequestBuilders
				.post("/api/professor/class").content(_content).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andDo(print())
			.andDo(document("Save KM_class By Professor",
				requestFields(fieldWithPath("Authorization").type(JsonFieldType.STRING).description("보안 토큰").optional(),
					fieldWithPath("seq").type(JsonFieldType.NUMBER).description("수업 번호").optional(),
					fieldWithPath("name").type(JsonFieldType.STRING).description("수업명"),
					fieldWithPath("startDate").type(JsonFieldType.STRING).description("수업 시작일"),
					fieldWithPath("endDate").type(JsonFieldType.STRING).description("수업 종료일"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("수업 내용").optional(),
					fieldWithPath("plannerDocName").type(JsonFieldType.STRING).description("강의 계획서").optional(),
					fieldWithPath("type").type(JsonFieldType.STRING).description("수업 종류").optional(),
					fieldWithPath("replyPermit_state").type(JsonFieldType.STRING).description("댓글 허용 여부").optional(),
					fieldWithPath("selectMenu").type(JsonFieldType.STRING).description("연관 메뉴").optional(),
					fieldWithPath("use_state").type(JsonFieldType.STRING).description("사용 여부").optional(),
					fieldWithPath("saveState").type(JsonFieldType.STRING).description("임시저장 여부").optional(),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("").optional(),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("참고").optional(),
					fieldWithPath("_links.delete.href").type(JsonFieldType.STRING).description("수업 삭제").optional(),
					fieldWithPath("_links.update.href").type(JsonFieldType.STRING).description("수업 수정").optional()),
				responseFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("수업 번호").optional(),
					fieldWithPath("name").type(JsonFieldType.STRING).description("수업명").optional(),
					fieldWithPath("startDate").type(JsonFieldType.STRING).description("수업 시작일"),
					fieldWithPath("endDate").type(JsonFieldType.STRING).description("수업 종료일"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("수업 내용").optional(),
					fieldWithPath("plannerDocName").type(JsonFieldType.STRING).description("강의 계획서").optional(),
					fieldWithPath("type").type(JsonFieldType.STRING).description("수업 종류").optional(),
					fieldWithPath("replyPermit_state").type(JsonFieldType.STRING).description("댓글 허용 여부").optional(),
					fieldWithPath("selectMenu").type(JsonFieldType.STRING).description("연관 메뉴").optional(),
					fieldWithPath("use_state").type(JsonFieldType.STRING).description("사용 여부").optional(),
					fieldWithPath("saveState").type(JsonFieldType.STRING).description("임시저장 여부").optional(),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("").optional(),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("참고").optional(),
					fieldWithPath("_links.delete.href").type(JsonFieldType.STRING).description("수업 삭제").optional(),
					fieldWithPath("_links.update.href").type(JsonFieldType.STRING).description("수업 수정").optional())));
	}

	@Test
	@Ignore
	@Memo("토큰을 가진 사용자(교수)가 클래스 정보를 접근 하는 경우")
	public void getKm_classInfo() throws Exception {
		// When
		this.mvc.perform(RestDocumentationRequestBuilders.get("/api/professor/class/{seq}", 3L).content(content))
			.andExpect(status().isOk()).andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE)).andDo(print())
			.andDo(document("Access KM_class Info by Professor", pathParameters(parameterWithName("seq").description("수업 번호")),
				requestFields(fieldWithPath("Authorization").type(JsonFieldType.STRING).description("보안 토큰")),
				responseFields(fieldWithPath("seq").type(JsonFieldType.NUMBER).description("수업 번호").optional(),
					fieldWithPath("name").type(JsonFieldType.STRING).description("수업명").optional(),
					fieldWithPath("startDate").type(JsonFieldType.STRING).description("수업 시작일"),
					fieldWithPath("endDate").type(JsonFieldType.STRING).description("수업 종료일"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("수업 내용").optional(),
					fieldWithPath("plannerDocName").type(JsonFieldType.STRING).description("강의 계획서").optional(),
					fieldWithPath("type").type(JsonFieldType.STRING).description("수업 종류").optional(),
					fieldWithPath("replyPermit_state").type(JsonFieldType.STRING).description("댓글 허용 여부").optional(),
					fieldWithPath("selectMenu").type(JsonFieldType.STRING).description("연관 메뉴").optional(),
					fieldWithPath("use_state").type(JsonFieldType.STRING).description("사용 여부").optional(),
					fieldWithPath("saveState").type(JsonFieldType.STRING).description("임시저장 여부").optional(),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("").optional(),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("참고").optional(),
					fieldWithPath("_links.delete.href").type(JsonFieldType.STRING).description("수업 삭제").optional(),
					fieldWithPath("_links.update.href").type(JsonFieldType.STRING).description("수업 수정").optional())));
	}

	@Test
	@Ignore
	@Memo("토큰을 가진 사용자(교수)가 클래스 리스트에 접근을 성공")
	public void getKm_classList() throws Exception {
		this.login_access();

		String bearer = resultToken.get("access_token").toString();
		String content = "{\"Authorization\" : \"Bearer " + bearer + "\"}";

		// When
		this.mvc.perform(get("/api/professor/class").content(content)).andExpect(status().isOk())
			.andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE)).andDo(print())
			.andDo(document("Access KM_class by Professor",
				requestFields(fieldWithPath("Authorization").type(JsonFieldType.STRING).description("보안 토큰")),
				responseFields(
					fieldWithPath("_embedded.kM_classVOList[0].seq").type(JsonFieldType.NUMBER).description("수업 번호").optional(),
					fieldWithPath("_embedded.kM_classVOList[0].name").type(JsonFieldType.STRING).description("수업명").optional(),
					fieldWithPath("_embedded.kM_classVOList[0].startDate").type(JsonFieldType.STRING).description("수업 시작일")
						.optional(),
					fieldWithPath("_embedded.kM_classVOList[0].endDate").type(JsonFieldType.STRING).description("수업 종료일")
						.optional(),
					fieldWithPath("_embedded.kM_classVOList[0].content").type(JsonFieldType.STRING).description("수업 내용")
						.optional(),
					fieldWithPath("_embedded.kM_classVOList[0].plannerDocName").type(JsonFieldType.STRING).description("강의 계획서")
						.optional(),
					fieldWithPath("_embedded.kM_classVOList[0].type").type(JsonFieldType.STRING).description("수업 종류").optional(),
					fieldWithPath("_embedded.kM_classVOList[0].replyPermit_state").type(JsonFieldType.STRING)
						.description("댓글 허용 여부").optional(),
					fieldWithPath("_embedded.kM_classVOList[0].selectMenu").type(JsonFieldType.STRING).description("연관 메뉴")
						.optional(),
					fieldWithPath("_embedded.kM_classVOList[0].use_state").type(JsonFieldType.STRING).description("사용 여부")
						.optional(),
					fieldWithPath("_embedded.kM_classVOList[0].saveState").type(JsonFieldType.STRING).description("임시저장 여부")
						.optional(),
					fieldWithPath("_embedded.kM_classVOList[0]._links.self.href").type(JsonFieldType.STRING).description("")
						.optional(),
					fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("").optional(),
					fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("참고").optional(),
					fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("").optional(),
					fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description("").optional(),
					fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description("").optional(),
					fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("").optional(),
					fieldWithPath("_links.delete.href").type(JsonFieldType.STRING).description("수업 삭제").optional(),
					fieldWithPath("_links.update.href").type(JsonFieldType.STRING).description("수업 수정").optional())));
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
