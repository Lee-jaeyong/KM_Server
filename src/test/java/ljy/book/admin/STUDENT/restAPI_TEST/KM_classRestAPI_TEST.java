package ljy.book.admin.STUDENT.restAPI_TEST;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import ljy.book.admin.CommonTestConfig;
import ljy.book.admin.common.object.CustomCodeCreator;
import ljy.book.admin.custom.anotation.Memo;
import ljy.book.admin.entity.KM_class;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.professor.service.impl.KM_Class_Professor_Service;
import ljy.book.admin.security.KM_UserService;

public class KM_classRestAPI_TEST extends CommonTestConfig {

	@Autowired
	KM_Class_Professor_Service KM_ClassService;

	@Autowired
	CustomCodeCreator customCodeCreator;

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
	@Memo("학생이 수업을 신청하는 경우")
	public void signUpClassForStu() throws Exception {
		this.mvc.perform(RestDocumentationRequestBuilders.post("/api/student/class/{code}", "ECCBC87E").content(content))
			.andDo(print()).andExpect(status().isOk())
			.andDo(document("Sign Up Class For Student", pathParameters(parameterWithName("code").description("수업 코드")),
				requestFields(fieldWithPath("Authorization").type(JsonFieldType.STRING).description("보안 토큰").optional()),
				responseFields(fieldWithPath("content").type(String.class).description("").optional(),
					fieldWithPath("_links.self.href").type(Integer.class).description("과제 번호").optional(),
					fieldWithPath("_links.profile.href").type(Integer.class).description("참고").optional(),
					fieldWithPath("_links.delete.href").type(Integer.class).description("과제 번호").optional())));
	}

	@Test
	@Ignore
	@Memo("학생이 수업의 정보를 가져오는 경우")
	public void getKmClassInfo() throws Exception {
		this.mvc.perform(RestDocumentationRequestBuilders.get("/api/student/class/{seq}", 3).content(content)).andDo(print())
			.andExpect(status().isOk())
			.andDo(document("Access KM_class Info by Student", pathParameters(parameterWithName("seq").description("수업 번호")),
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
	@Memo("학생이 듣는 수업리스트를 가져오는 경우")
	public void getKmClassList() throws Exception {
		this.mvc.perform(RestDocumentationRequestBuilders.get("/api/student/class").content(content)).andDo(print())
			.andExpect(status().isOk())
			.andDo(document("Access KM_class by Student",
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
					fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("").optional())));
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
