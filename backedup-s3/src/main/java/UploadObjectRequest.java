import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.StorageClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

class UploadObjectRequest {
	private String bucket;
	private String remotePath;
	private LocalFile localPathFile;
	private String storageClass;
	private Logger logger = Logger.getLogger(this.getClass().getName());

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
		try {
			return new PutObjectRequest(bucket, remotePath, new FileInputStream(localPathFile.toFile()), objectMetadata())
					.withStorageClass(StorageClass.fromValue(storageClass));
		} catch(FileNotFoundException e) {
			logger.warning(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	private ObjectMetadata objectMetadata() {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(localPathFile.toFile().length());
		objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
		return objectMetadata;
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
