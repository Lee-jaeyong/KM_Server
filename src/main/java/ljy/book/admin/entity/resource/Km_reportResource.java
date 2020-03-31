package ljy.book.admin.entity.resource;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;

import ljy.book.admin.professor.restAPI.KM_Report_Professor_RestController;
import ljy.book.admin.request.KM_reportVO;

public class Km_reportResource extends EntityModel<KM_reportVO> {

	public Km_reportResource(KM_reportVO content) {
		super(content);
		add(ControllerLinkBuilder.linkTo(KM_Report_Professor_RestController.class).slash("").withSelfRel());
	}

}
