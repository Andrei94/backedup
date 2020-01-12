package subscription;

public class SubscriptionRequest {
	private long usedSize;
	private String user;

	public SubscriptionRequest(long usedSize, String user) {
		this.usedSize = usedSize;
		this.user = user;
	}

	public long getUsedSize() {
		return usedSize;
	}

	public String getUser() {
		return user;
	}
}
