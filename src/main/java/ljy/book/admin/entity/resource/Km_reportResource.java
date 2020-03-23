package ljy.book.admin.entity.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import ljy.book.admin.dto.Km_ReportDTO;
import ljy.book.admin.restAPI.KM_ReportRestController;

public class Km_reportResource extends Resource<Km_ReportDTO> {

	public Km_reportResource(Km_ReportDTO content, Link... links) {
		super(content, links);
		add(ControllerLinkBuilder.linkTo(KM_ReportRestController.class).slash("").withSelfRel());
	}

}
