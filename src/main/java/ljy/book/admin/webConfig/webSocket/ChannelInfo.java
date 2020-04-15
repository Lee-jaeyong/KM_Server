package ljy.book.admin.webConfig.webSocket;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChannelInfo {
	String classCode;
	String userName;
	String content;

	public static ChannelInfo create(String classCode) {
		ChannelInfo channel = new ChannelInfo();
		channel.setClassCode(classCode);
		return channel;
	}
}
