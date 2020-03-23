package ljy.book.admin.entity.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import ljy.book.admin.request.KM_classVO;
import ljy.book.admin.restAPI.KM_ClassRestController;

public class Km_classResource extends Resource<KM_classVO> {

	public Km_classResource(KM_classVO content, Link... links) {
		super(content, links);
		add(ControllerLinkBuilder.linkTo(KM_ClassRestController.class).slash("").withSelfRel());
	}

}
