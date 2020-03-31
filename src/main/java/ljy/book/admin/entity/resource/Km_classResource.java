package ljy.book.admin.entity.resource;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import ljy.book.admin.entity.KM_class;
import ljy.book.admin.professor.restAPI.KM_ClassRestController;
import ljy.book.admin.request.KM_classVO;

public class Km_classResource extends EntityModel<KM_classVO> {

	public Km_classResource(KM_classVO content) {
		super(content);
		add(ControllerLinkBuilder.linkTo(KM_ClassRestController.class).slash("").withSelfRel());
	}

}
