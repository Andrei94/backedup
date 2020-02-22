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
import java.util.logging.Level;
import java.util.logging.Logger;

public class S3Adapter {
	private Logger logger = Logger.getLogger(this.getClass().getName());
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
			logger.log(Level.SEVERE, "An error occurred while uploading to S3", ex);
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
			logger.info("Downloaded " + bytesTransferred + " bytes to " + destPath);
			File downloadedFile = new File(destPath, name);
			if(bytesTransferred == 0 && !downloadedFile.exists())
				return Optional.empty();
			return Optional.of(LocalFile.fromFile(downloadedFile));
		} catch(RuntimeException | InterruptedException e) {
			logger.log(Level.SEVERE, "An error occured while downloading", e);
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

	public void updateCredentials(UserCredentials credentials) {
		transferManager = TransferManagerBuilder.standard().withS3Client(AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicSessionCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey(), credentials.getSessionToken())))
				.withRegion(Regions.EU_CENTRAL_1)
				.build()).build();
	}

	public Optional<LocalFile> downloadFile(String file) {
		File outputFile = new File(file);
		try {
			transferManager.download("backedup-storage-2", file, outputFile).waitForCompletion();
			return Optional.of(LocalFile.fromFile(outputFile));
		} catch(InterruptedException e) {
			logger.log(Level.SEVERE, "An error occurred while downloading", e);
			return Optional.empty();
		}
	}
}