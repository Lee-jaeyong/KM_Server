package ljy_yjw.team_manage.system.dbConn.mybatis;

import org.apache.ibatis.annotations.Mapper;

import ljy_yjw.team_manage.system.domain.entity.TodoList;

@Mapper
public interface TodoListDAO {
	void update(TodoList todoList);
	
	void updateIng(long seq);

	void updateIngFaild(long seq);
	
	void delete(long seq);
}
