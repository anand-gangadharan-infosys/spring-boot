package hello.user.domain;

public class Greeting {

	private final long id;
	private final String content;

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public Greeting(String content) {
		this.content = content;
		id = 1;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
