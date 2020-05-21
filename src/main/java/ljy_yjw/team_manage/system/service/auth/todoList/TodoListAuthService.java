package ljy_yjw.team_manage.system.service.auth.todoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ljy_yjw.team_manage.system.dbConn.jpa.TodoListAPI;
import ljy_yjw.team_manage.system.domain.entity.Users;
import ljy_yjw.team_manage.system.exception.exceptions.NotFoundException;

@Service
public class TodoListAuthService {

	@Autowired
	TodoListAPI todoListAPI;

	public void authCheck(long seq, Users user) throws NotFoundException {
		if(todoListAPI.findBySeqAndUser_Id(seq,user.getId()) == null) {
			throw new NotFoundException("해당 번호의 TodoList가 존재하지 않습니다.");
		}
	}
}
