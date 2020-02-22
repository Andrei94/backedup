package uploader;

import adapter.S3Adapter;
import adapter.UploadObjectRequest;
import authentication.User;
import file.LocalFile;
import subscription.Subscription;
import subscription.SubscriptionChecker;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class S3ObjectUploader implements ObjectUploader {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private final S3Adapter adapter;
	private final LocalFileWalker walker;
	private User user;
	private SubscriptionChecker subscriptionStorageChecker;

	public S3ObjectUploader(S3Adapter adapter, LocalFileWalker walker, SubscriptionChecker subscriptionStorageChecker) {
		this.adapter = adapter;
		this.walker = walker;
		this.subscriptionStorageChecker = subscriptionStorageChecker;
	}

	@Override
	public boolean uploadDirectory(Path path) {
		return uploadDirectory(LocalFile.fromPath(path));
	}

	@Override
	public void setLoggedInUser(User user) {
		this.user = user;
		user.getCredentials().ifPresent(adapter::updateCredentials);
	}

	@Override
	public boolean uploadFile(Path path) {
		LocalFile file = LocalFile.fromPath(path);
		Subscription subscription = subscriptionStorageChecker.checkSubscription(file.getSize(), user.getName());
		if(!subscription.isValid())
			return false;
		return uploadFile(file, subscription);
	}

	boolean uploadDirectory(LocalFile directory) {
		try {
			List<LocalFile> filesFromDirectory = walker.walkTreeFromRoot(directory)
					.filter(LocalFile::isFile)
					.collect(Collectors.toList());
			long dirSize = filesFromDirectory.parallelStream().map(LocalFile::getSize).reduce(0L, Long::sum);
			Subscription subscription = subscriptionStorageChecker.checkSubscription(dirSize, user.getName());
			if(!subscription.isValid())
				return false;
			return filesFromDirectory
					.parallelStream()
					.allMatch(localFile -> uploadFileFrom(directory, localFile, subscription));
		} catch(IOException | SecurityException ex) {
			logger.log(Level.SEVERE, "An error occurred while uploading " + directory.getPath(), ex);
			return false;
		}
	}

	boolean uploadFileFrom(LocalFile directory, LocalFile localFile, Subscription subscription) {
		if(user == null || !user.isAuthenticated())
			return false;
		UploadObjectRequest uploadRequest = new UploadObjectRequest()
				.withBucket(subscription.getBucketName())
				.withRemoteFile(subscription.getUserPath() + adapter.toFileInRemoteFolder(directory.getName(), directory.relativize(localFile)))
				.withLocalFile(localFile)
				.withStorageClass(subscription.getStorageClass());
		logger.info("Uploading " + uploadRequest);
		return adapter.putObject(uploadRequest);
	}

	boolean uploadFile(LocalFile localFile, Subscription subscription) {
		if(user == null || !user.isAuthenticated())
			return false;
		UploadObjectRequest uploadRequest = new UploadObjectRequest()
				.withBucket(subscription.getBucketName())
				.withRemoteFile(subscription.getUserPath() + localFile.getPath())
				.withLocalFile(localFile)
				.withStorageClass(subscription.getStorageClass());
		logger.info("Uploading " + uploadRequest);
		return adapter.putObject(uploadRequest);
	}
}
