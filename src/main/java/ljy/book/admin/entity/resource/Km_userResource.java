package ljy.book.admin.entity.resource;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;

import ljy.book.admin.dto.Km_userDTO;
import ljy.book.admin.entity.KM_user;
import ljy.book.admin.restAPI.KM_UserRestController;

public class Km_userResource extends EntityModel<KM_user> {

	public Km_userResource(KM_user content) {
		super(content);
		add(ControllerLinkBuilder.linkTo(KM_UserRestController.class).slash("").withSelfRel());
	}

}
