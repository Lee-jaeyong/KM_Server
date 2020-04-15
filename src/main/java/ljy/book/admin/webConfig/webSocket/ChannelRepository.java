package ljy.book.admin.webConfig.webSocket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

@Repository
public class ChannelRepository {
	private Map<String, ChannelInfo> channelMap;

	@PostConstruct
	public void init() {
		channelMap = new LinkedHashMap<String, ChannelInfo>();
	}

	public List<ChannelInfo> findAllChannel() {
		List channel = new ArrayList<ChannelInfo>();
		Collections.reverse(channel);
		return channel;
	}

	public ChannelInfo findRoomById(String code) {
		return channelMap.get(code);
	}

	public ChannelInfo createChannel(String code) {
		ChannelInfo channel = ChannelInfo.create(code);
		channelMap.put(channel.getClassCode(), channel);
		return channel;
	}
}
