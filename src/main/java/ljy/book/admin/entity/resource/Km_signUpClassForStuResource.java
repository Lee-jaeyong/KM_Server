package ljy.book.admin.entity.resource;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;

import ljy.book.admin.request.KM_signUpClassForStuVO;
import ljy.book.admin.student.restAPI.KM_Class_Student_RestController;

public class Km_signUpClassForStuResource extends EntityModel<KM_signUpClassForStuVO> {

	public Km_signUpClassForStuResource(KM_signUpClassForStuVO content) {
		super(content);
		add(ControllerLinkBuilder.linkTo(KM_Class_Student_RestController.class).slash("").withSelfRel());
	}

}
