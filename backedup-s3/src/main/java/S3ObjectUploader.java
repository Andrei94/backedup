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
	public void uploadDirectory(Path path) {
		uploadDirectory(LocalFile.fromPath(path));
	}

	@Override
	public void setLoggedInUsername(String username) {
		this.username = username;
	}

	void uploadDirectory(LocalFile directory) {
		walker.walkTreeFromRoot(directory)
				.filter(LocalFile::isFile)
				.collect(Collectors.toList())
				.parallelStream()
				.forEach(localFile -> uploadFileFrom(directory, localFile));
	}

	boolean uploadFileFrom(LocalFile directory, LocalFile localFile) {
		if(username == null || username.isEmpty())
			return false;
		UploadObjectRequest uploadRequest = new UploadObjectRequest()
				.withBucket("backedup-storage")
				.withRemoteFile("816033825058:" + username + "/" + adapter.toFileInRemoteFolder(directory.getName(), directory.relativize(localFile)))
				.withLocalFile(localFile)
				.withStorageClass("INTELLIGENT_TIERING");
		logger.info("Uploading " + uploadRequest);
		return adapter.putObject(uploadRequest);
	}
}
