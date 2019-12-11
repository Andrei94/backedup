package uploader;

import adapter.S3Adapter;
import adapter.UploadObjectRequest;
import file.LocalFile;

import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class S3ObjectUploader implements ObjectUploader {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private final S3Adapter adapter;
	private final LocalFileWalker walker;
	private String username;

	public S3ObjectUploader(S3Adapter adapter, LocalFileWalker walker) {
		this.adapter = adapter;
		this.walker = walker;
	}

	@Override
	public boolean uploadDirectory(Path path) {
		return uploadDirectory(LocalFile.fromPath(path));
	}

	@Override
	public void setLoggedInUsername(String username) {
		this.username = username;
	}

	boolean uploadDirectory(LocalFile directory) {
		try {
			return walker.walkTreeFromRoot(directory)
					.filter(LocalFile::isFile)
					.collect(Collectors.toList())
					.parallelStream()
					.anyMatch(localFile -> uploadFileFrom(directory, localFile));
		} catch(RuntimeException ex) {
			return false;
		}
	}

	boolean uploadFileFrom(LocalFile directory, LocalFile localFile) {
		if(username == null || username.isEmpty())
			return false;
		UploadObjectRequest uploadRequest = new UploadObjectRequest()
				.withBucket("backedup-storage")
				.withRemoteFile(username + "/" + adapter.toFileInRemoteFolder(directory.getName(), directory.relativize(localFile)))
				.withLocalFile(localFile)
				.withStorageClass("INTELLIGENT_TIERING");
		logger.info("Uploading " + uploadRequest);
		return adapter.putObject(uploadRequest);
	}
}
