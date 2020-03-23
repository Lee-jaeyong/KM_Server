package ljy.book.admin.entity.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import ljy.book.admin.dto.Km_userLoginDTO;
import ljy.book.admin.restAPI.KM_UserRestController;


public class Km_userLoginResource extends Resource<Km_userLoginDTO> {

	public Km_userLoginResource(Km_userLoginDTO content, Link... links) {
		super(content, links);
		add(ControllerLinkBuilder.linkTo(KM_UserRestController.class).slash("").withSelfRel());
	}

}
