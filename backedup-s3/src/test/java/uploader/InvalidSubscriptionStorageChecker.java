package uploader;

import subscription.Subscription;

public class InvalidSubscriptionStorageChecker implements subscription.SubscriptionChecker {
	private String host;

	@Override
	public Subscription checkSubscription(long usedSize, String username) {
		return new Subscription().withFreeSize(0);
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}
}
