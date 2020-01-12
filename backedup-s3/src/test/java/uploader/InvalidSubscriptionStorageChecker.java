package uploader;

import subscription.Subscription;

public class InvalidSubscriptionStorageChecker implements subscription.SubscriptionChecker {
	@Override
	public Subscription checkSubscription(long usedSize, String username) {
		return new Subscription().withFreeSize(0);
	}
}
