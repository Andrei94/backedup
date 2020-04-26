package uploader;

import subscription.Subscription;
import subscription.SubscriptionChecker;

public class ValidSubscriptionStorageChecker implements SubscriptionChecker {
	private String host;

	@Override
	public Subscription checkSubscription(long usedSize, String username) {
		long oneTB = 1024L * 1024L * 1024L * 1024L;
		return new Subscription().withBucketName("backedup-storage-2").withFreeSize(oneTB - usedSize).withStorageClass("INTELLIGENT_TIERING").withUserPath(username + "/");
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}
}
