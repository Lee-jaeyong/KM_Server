package ljy.book.admin.webConfig.webSocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class KM_RealTimeClass {

	@Autowired
	private SimpMessagingTemplate webSocket;

	@MessageMapping("/sendMessage")
	public void ReceiveMessage(ChannelInfo msg) throws Exception {
		webSocket.convertAndSend("/topics/video/" + msg.getClassCode(), msg.getContent());
	}

	@MessageMapping("/join")
	public void createChannel(String channel) {
		List<String> joinChannel = ChannelInfo.ChnnalList.get(channel);
		if(joinChannel == null) {
			System.out.println("채널 없음");
		}
		webSocket.convertAndSend("/topics/" + channel, channel + " 수업을 생성하였습니다.");
	}
}
