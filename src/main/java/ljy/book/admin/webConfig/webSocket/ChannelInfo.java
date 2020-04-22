package ljy.book.admin.webConfig.webSocket;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChannelInfo {
	public static HashMap<String, ArrayList<String>> ChnnalList = new HashMap<String, ArrayList<String>>();

	String classCode;
	String userName;
	String content;

	public static ChannelInfo create(String classCode) {
		ChannelInfo channel = new ChannelInfo();
		channel.setClassCode(classCode);
		return channel;
	}
}
