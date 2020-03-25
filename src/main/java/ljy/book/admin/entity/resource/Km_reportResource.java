package ljy.book.admin.entity.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import ljy.book.admin.request.KM_reportVO;
import ljy.book.admin.restAPI.KM_ReportRestController;

public class Km_reportResource extends Resource<KM_reportVO> {

	public Km_reportResource(KM_reportVO content, Link... links) {
		super(content, links);
		add(ControllerLinkBuilder.linkTo(KM_ReportRestController.class).slash("").withSelfRel());
	}

}
