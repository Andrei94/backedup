package adapter;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import file.LocalFile;

import java.io.File;
import java.util.Optional;

public class S3Adapter {
	private final AmazonS3 client;
	private TransferManager transferManager;

	public S3Adapter() {
		this(AmazonS3ClientBuilder
				.standard()
				.withCredentials(new ProfileCredentialsProvider("backedup-storage"))
				.withRegion(Regions.US_EAST_1)
				.build());
	}

	public S3Adapter(AmazonS3 client) {
		this.client = client;
		this.transferManager = TransferManagerBuilder.standard().withS3Client(client).build();
	}

	public boolean putObject(UploadObjectRequest request) {
		try {
			PutObjectResult result = client.putObject(request.toS3PutObjectRequest());
			return result.getETag().equals(result.getContentMd5());
		} catch(SdkClientException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public String toFileInRemoteFolder(String folderName, String localFile) {
		return makeS3FolderFromName(folderName) + localFile;
	}

	private String makeS3FolderFromName(String folderName) {
		return folderName + "/";
	}

	public Optional<LocalFile> downloadDirectoryExcludingGlacier(String name, String destPath) {
		try {
			transferManager.downloadDirectory("backedup-storage", name, new File(destPath), true,
					objectSummary -> !(objectSummary.getStorageClass().equals("GLACIER") && objectSummary.getStorageClass().equals("DEEP_ARCHIVE"))).waitForCompletion();
			return Optional.of(LocalFile.fromFile(new File(destPath, name)));
		} catch(RuntimeException | InterruptedException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public void shutdownTransferManager() {
		transferManager.shutdownNow(true);
	}
}