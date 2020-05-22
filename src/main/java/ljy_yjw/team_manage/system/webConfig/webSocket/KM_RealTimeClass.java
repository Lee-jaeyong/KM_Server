package ljy_yjw.team_manage.system.webConfig.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class KM_RealTimeClass {

	@Autowired
	private SimpMessagingTemplate webSocket;

//	@MessageMapping("/insertPlan")
//	public void insertPlan(String code, String msg) {
//		webSocket.convertAndSend("/topics/" + code, msg);
//	}
}
