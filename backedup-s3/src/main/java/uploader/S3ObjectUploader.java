package uploader;

import adapter.S3Adapter;
import adapter.UploadObjectRequest;
import authentication.User;
import file.LocalFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class S3ObjectUploader implements ObjectUploader {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private final S3Adapter adapter;
	private final LocalFileWalker walker;
	private User user;

	public S3ObjectUploader(S3Adapter adapter, LocalFileWalker walker) {
		this.adapter = adapter;
		this.walker = walker;
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

	boolean uploadDirectory(LocalFile directory) {
		try {
			return walker.walkTreeFromRoot(directory)
					.filter(LocalFile::isFile)
					.collect(Collectors.toList())
					.parallelStream()
					.allMatch(localFile -> uploadFileFrom(directory, localFile));
		} catch(IOException | RuntimeException ex) {
			logger.log(Level.SEVERE, "An error occurred while uploading " + directory.getPath(), ex);
			return false;
		}
	}

	boolean uploadFileFrom(LocalFile directory, LocalFile localFile) {
		if(user == null || !user.isAuthenticated())
			return false;
		UploadObjectRequest uploadRequest = new UploadObjectRequest()
				.withBucket("backedup-storage-2")
				.withRemoteFile(user.getName() + "/" + adapter.toFileInRemoteFolder(directory.getName(), directory.relativize(localFile)))
				.withLocalFile(localFile)
				.withStorageClass("INTELLIGENT_TIERING");
		logger.info("Uploading " + uploadRequest);
		return adapter.putObject(uploadRequest);
	}
}
