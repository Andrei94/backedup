import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.StorageClass;

import java.io.File;

class UploadObjectRequest {
	private String bucket;
	private String remoteFile;
	private File localFile;
	private String storageClass;

	UploadObjectRequest withBucket(String bucketName) {
		this.bucket = bucketName;
		return this;
	}

	UploadObjectRequest withRemoteFile(String remoteFile) {
		this.remoteFile = remoteFile;
		return this;
	}

	UploadObjectRequest withLocalFile(File localFile) {
		this.localFile = localFile;
		return this;
	}

	UploadObjectRequest withStorageClass(String storageClass) {
		this.storageClass = storageClass;
		return this;
	}

	PutObjectRequest toS3PutObjectRequest() {
		return new PutObjectRequest(bucket, remoteFile, localFile).withStorageClass(StorageClass.fromValue(storageClass));
	}
}
