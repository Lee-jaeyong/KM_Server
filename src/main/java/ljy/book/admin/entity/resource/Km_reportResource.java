package ljy.book.admin.entity.resource;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;

import ljy.book.admin.request.KM_reportVO;
import ljy.book.admin.restAPI.KM_ReportRestController;

public class Km_reportResource extends EntityModel<KM_reportVO> {

	public Km_reportResource(KM_reportVO content) {
		super(content);
		add(ControllerLinkBuilder.linkTo(KM_ReportRestController.class).slash("").withSelfRel());
	}

}
