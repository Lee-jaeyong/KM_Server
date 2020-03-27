package ljy.book.admin.entity.resource;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;

import ljy.book.admin.dto.Km_userLoginDTO;
import ljy.book.admin.restAPI.KM_UserRestController;

public class Km_userLoginResource extends EntityModel<Km_userLoginDTO> {

	public Km_userLoginResource(Km_userLoginDTO content) {
		super(content);
		add(ControllerLinkBuilder.linkTo(KM_UserRestController.class).slash("").withSelfRel());
	}

}
