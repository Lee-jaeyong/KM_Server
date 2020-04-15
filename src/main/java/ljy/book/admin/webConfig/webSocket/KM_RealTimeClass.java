package ljy.book.admin.webConfig.webSocket;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class KM_RealTimeClass {

	@Autowired
	ChannelRepository channelRepository;

	@Autowired
	private SimpMessagingTemplate webSocket;

	@Autowired
	ObjectMapper objectMapper;

	@MessageMapping("/webRTCConnect/sdp")
	public void sdpSender(String sdp) throws Exception {
		System.out.println(sdp);
		webSocket.convertAndSend("/topics/webRTCRequest", sdp);
	}

	@MessageMapping("/webRTCConnect/candidate")
	public void candidateSender(String candidate) throws Exception {
		webSocket.convertAndSend("/topics/webRTCRequest", candidate);
	}

	@MessageMapping("/webRTCConnect/answer")
	public void candidateAnswer(String answer) throws Exception {
		webSocket.convertAndSend("/topics/webRTCResponse", answer);
	}

	@MessageMapping("/rtcConn")
	public void rtcConn(String sdp) {
		webSocket.convertAndSend("/topics/a", sdp);
	}

	@MessageMapping("/rtcFinal")
	public void finalConn(String sdp) {
		webSocket.convertAndSend("/topics/a", sdp);
	}

	@MessageMapping("/sdp")
	public void a(String sdp) {
		webSocket.convertAndSend("/topics/testchat", sdp);
	}

	@MessageMapping("/join")
	public void join(ChannelInfo msg) {
		webSocket.convertAndSend("/topics/" + msg.getClassCode(), msg.getUserName() + "님이 입장하셨습니다.");
	}

	@MessageMapping("/sendMessage")
	public void ReceiveMessage(ChannelInfo msg) throws Exception {
		webSocket.convertAndSend("/topics/video/" + msg.getClassCode(), msg.getContent());
	}

	@MessageMapping("/create")
	public void createChannel(String channel) {
		channelRepository.createChannel(channel);
		webSocket.convertAndSend("/topics/" + channel, channel + " 수업을 생성하였습니다.");
	}
}
