package subscription;

public class Subscription {
	private long freeSize;
	private String bucketName;
	private String storageClass;
	private String userPath;

	public String getBucketName() {
		return bucketName;
	}

	public String getStorageClass() {
		return storageClass;
	}

	public String getUserPath() {
		return userPath;
	}

	public Subscription withFreeSize(long freeSize) {
		this.freeSize = freeSize;
		return this;
	}

	public Subscription withBucketName(String bucketName) {
		this.bucketName = bucketName;
		return this;
	}

	public Subscription withStorageClass(String storageClass) {
		this.storageClass = storageClass;
		return this;
	}

	public Subscription withUserPath(String userPath) {
		this.userPath = userPath;
		return this;
	}

	public boolean isValid() {
		return freeSize > 0;
	}
}
