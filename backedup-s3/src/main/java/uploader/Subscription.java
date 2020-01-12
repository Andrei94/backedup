package uploader;

public class Subscription {
	private long freeSize;
	private String bucketName;
	private String storageClass;
	private String userPath;

	public long getFreeSize() {
		return freeSize;
	}

	public void setFreeSize(long freeSize) {
		this.freeSize = freeSize;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getStorageClass() {
		return storageClass;
	}

	public void setStorageClass(String storageClass) {
		this.storageClass = storageClass;
	}

	public String getUserPath() {
		return userPath;
	}

	public void setUserPath(String userPath) {
		this.userPath = userPath;
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
}
