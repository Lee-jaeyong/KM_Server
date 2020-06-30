package ljy_yjw.team_manage.system.custom.util.impl;

import javax.inject.Named;

import org.springframework.stereotype.Component;

import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;
import ljy_yjw.team_manage.system.custom.object.CustomEntityModel.Link;
import ljy_yjw.team_manage.system.custom.util.EntityFactory;

@SuppressWarnings("unchecked")
@Component
@Named("objectEntityFactory")
public class ObjectEntityFactory implements EntityFactory {

	@Override
	public <T> CustomEntityModel<T> get(T obj, String str) {
		return (CustomEntityModel<T>) new CustomEntityModel<Object>(obj, this, str, Link.NOT_INCLUDE);
	}

}
