import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.StorageClass;

class UploadObjectRequest {
	private String bucket;
	private String remotePath;
	private LocalFile localPathFile;
	private String storageClass;

	UploadObjectRequest withBucket(String bucketName) {
		this.bucket = bucketName;
		return this;
	}

	UploadObjectRequest withRemoteFile(String remotePath) {
		this.remotePath = remotePath;
		return this;
	}

	UploadObjectRequest withLocalFile(LocalFile localPathFile) {
		this.localPathFile = localPathFile;
		return this;
	}

	UploadObjectRequest withStorageClass(String storageClass) {
		this.storageClass = storageClass;
		return this;
	}

	PutObjectRequest toS3PutObjectRequest() {
		return new PutObjectRequest(bucket, remotePath, localPathFile.toFile()).withStorageClass(StorageClass.fromValue(storageClass));
	}

	public String getBucket() {
		return bucket;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public LocalFile getLocalPathFile() {
		return localPathFile;
	}

	public String getStorageClass() {
		return storageClass;
	}

	@Override
	public String toString() {
		return "UploadObjectRequest{" +
				"bucket='" + bucket + '\'' +
				", remotePath='" + remotePath + '\'' +
				", localPathFile=" + localPathFile +
				", storageClass='" + storageClass + '\'' +
				'}';
	}
}
