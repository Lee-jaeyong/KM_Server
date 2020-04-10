package ljy.book.admin.professor.restAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import ljy.book.admin.webConfig.ChatMessage;

@Controller
public class KM_RealTimeClass {

	@Autowired
	private SimpMessagingTemplate webSocket;

	@MessageMapping("/hello")
	public String ReceiveMessage(ChatMessage msg) throws Exception {
		System.out.println(msg.getContent());
		webSocket.convertAndSend("/topics/testchat",msg.getContent());
		webSocket.convertAndSend("/topics/testchat/1",msg.getContent());
		return "fsdfjskdhfsdjk";
	}
}
