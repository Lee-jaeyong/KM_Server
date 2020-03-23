package ljy.book.admin.entity.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import ljy.book.admin.dto.Km_userDTO;
import ljy.book.admin.restAPI.KM_UserRestController;

public class Km_userResource extends Resource<Km_userDTO> {

	public Km_userResource(Km_userDTO content, Link... links) {
		super(content, links);
		add(ControllerLinkBuilder.linkTo(KM_UserRestController.class).slash("").withSelfRel());
	}

}
