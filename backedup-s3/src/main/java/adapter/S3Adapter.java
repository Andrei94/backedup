package adapter;

import authentication.UserCredentials;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import file.LocalFile;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class S3Adapter {
	private TransferManager transferManager;

	public S3Adapter(UserCredentials credentials) {
		this(TransferManagerBuilder.standard().withS3Client(AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicSessionCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey(), credentials.getSessionToken())))
				.withRegion(Regions.EU_CENTRAL_1)
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

	public Optional<LocalFile> downloadDirectory(String name, String destPath) {
		try {
			long bytesTransferred = downloadAsync(name, destPath);
			File downloadedFile = new File(destPath, name);
			if(bytesTransferred == 0 && !downloadedFile.exists())
				return Optional.empty();
			return Optional.of(LocalFile.fromFile(downloadedFile));
		} catch(RuntimeException | InterruptedException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	long downloadAsync(String name, String destPath) throws InterruptedException {
		AtomicLong bytesTransferred = new AtomicLong(0);
		MultipleFileDownload multipleFileDownload = transferManager.downloadDirectory("backedup-storage-2", name, new File(destPath), true);
		multipleFileDownload.addProgressListener((ProgressListener) progressEvent -> bytesTransferred.addAndGet(progressEvent.getBytes()));
		multipleFileDownload.waitForCompletion();
		return bytesTransferred.longValue();
	}

	public void shutdownTransferManager() {
		transferManager.shutdownNow(true);
	}
}