package ljy_yjw.team_manage.system.webConfig.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

@Component
public class StompHandler extends ChannelInterceptorAdapter {

	@Autowired
	TokenStore tokenStore;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		if (StompCommand.CONNECT == accessor.getCommand()) {
			String jwt = accessor.getFirstNativeHeader("Authorization");
			if(jwt == null) {
				accessor = null;
				return null;
			}
			String tokenValue = jwt.replace("bearer", "").trim();
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
			if (accessToken == null) {
				accessor = null;
				return null;
			}
		}
		return message;
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		if(accessor == null) {
			return;
		}
		switch (accessor.getCommand()) {
		case CONNECT:
			System.out.println("connect");
			break;
		case DISCONNECT:
			System.out.println("disConnect");
			break;
		default:
			break;
		}
	}
}