package ljy.book.admin.webConfig.webSocket;

public class ChatMessage {
	private String name;
	private String session;
	private String content;

	public ChatMessage() {
	}

	public ChatMessage(String name, String id, String content) {
		this.name = name;
		this.session = id;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public String getSessionId() {
		return session;
	}

	public String getContent() {
		return content;
	}

	public void setSessionId(String sessionid) {
		this.session = sessionid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
