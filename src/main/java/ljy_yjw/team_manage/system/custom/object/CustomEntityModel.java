package ljy_yjw.team_manage.system.custom.object;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;;

public class CustomEntityModel<T> extends EntityModel<T> {
	public CustomEntityModel(T entity, Object controller, String content, Link link) {
		super(entity);
		if (content != null)
			add(linkTo(controller.getClass()).slash(content).withSelfRel());
		else
			add(linkTo(controller.getClass()).withSelfRel());
		if (link == Link.ALL) {
			add(linkTo(controller.getClass()).slash(content).withRel("delete"));
			add(linkTo(controller.getClass()).slash(content).withRel("update"));
			add(linkTo(controller.getClass()).slash("docs/index.html").withRel("profile"));
			return;
		}

		if (link == Link.DELETE) {
			add(linkTo(controller.getClass()).slash(content).withRel("delete"));
			add(linkTo(controller.getClass()).slash("docs/index.html").withRel("profile"));
			return;
		}

		if (link == Link.UPDATE) {
			add(linkTo(controller.getClass()).slash(content).withRel("update"));
			add(linkTo(controller.getClass()).slash("docs/index.html").withRel("profile"));
			return;
		}

	}

	public enum Link {
		ALL, DELETE, UPDATE, INSERT, NOT_INCLUDE
	}
}
