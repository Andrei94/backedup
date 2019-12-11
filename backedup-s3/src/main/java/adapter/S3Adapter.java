package adapter;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import file.LocalFile;

import java.io.File;
import java.util.Optional;

public class S3Adapter {
	private TransferManager transferManager;

	public S3Adapter() {
		this(TransferManagerBuilder.standard().withS3Client(AmazonS3ClientBuilder
				.standard()
				.withCredentials(new ProfileCredentialsProvider("backedup-storage"))
				.withRegion(Regions.US_EAST_1)
				.build()).build());
	}

	public S3Adapter(TransferManager transferManager) {
		this.transferManager = transferManager;
	}

	public boolean putObject(UploadObjectRequest request) {
		try {
			uploadAsync(request).waitForCompletion();
			return true;
		} catch(SdkClientException | InterruptedException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	Upload uploadAsync(UploadObjectRequest request) {
		return transferManager.upload(request.toS3PutObjectRequest());
	}

	public String toFileInRemoteFolder(String folderName, String localFile) {
		return makeS3FolderFromName(folderName) + localFile;
	}

	private String makeS3FolderFromName(String folderName) {
		return folderName + "/";
	}

	public Optional<LocalFile> downloadDirectoryExcludingGlacier(String name, String destPath) {
		try {
			downloadAsync(name, destPath).waitForCompletion();
			return Optional.of(LocalFile.fromFile(new File(destPath, name)));
		} catch(RuntimeException | InterruptedException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	MultipleFileDownload downloadAsync(String name, String destPath) {
		return transferManager.downloadDirectory("backedup-storage", name, new File(destPath), true,
				objectSummary -> !(objectSummary.getStorageClass().equals("GLACIER") && objectSummary.getStorageClass().equals("DEEP_ARCHIVE")));
	}

	public void shutdownTransferManager() {
		transferManager.shutdownNow(true);
	}
}