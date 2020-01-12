package subscription;

public interface SubscriptionChecker {
	Subscription checkSubscription(long usedSize, String username);
}
