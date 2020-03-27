package ljy.book.admin.entity.resource;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;

import ljy.book.admin.request.KM_classVO;
import ljy.book.admin.restAPI.KM_ClassRestController;

public class Km_classResource extends EntityModel<KM_classVO> {

	public Km_classResource(KM_classVO content) {
		super(content);
		add(ControllerLinkBuilder.linkTo(KM_ClassRestController.class).slash("").withSelfRel());
	}

}
