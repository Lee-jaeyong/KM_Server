package ljy_yjw.team_manage.system.custom.util;

import ljy_yjw.team_manage.system.custom.object.CustomEntityModel;

public interface EntityFactory {
	<T> CustomEntityModel<T> get(T t, String str);
}
