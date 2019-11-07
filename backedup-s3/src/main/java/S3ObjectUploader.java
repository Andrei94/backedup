import java.util.logging.Logger;
import java.util.stream.Collectors;

public class S3ObjectUploader {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private final S3Adapter adapter;
	private final LocalFileWalker walker;

	public S3ObjectUploader(S3Adapter adapter, LocalFileWalker walker) {
		this.adapter = adapter;
		this.walker = walker;
	}

	boolean uploadFileFrom(LocalFile directory, LocalFile localFile) {
		UploadObjectRequest uploadRequest = new UploadObjectRequest()
				.withBucket("backedup-storage")
				.withRemoteFile(adapter.toFileInRemoteFolder(directory.getName(), directory.relativize(localFile)))
				.withLocalFile(localFile)
				.withStorageClass("STANDARD");
		logger.info("Uploading " + uploadRequest);
		return adapter.putObject(uploadRequest);
	}

	void uploadDirectory(LocalFile directory) {
		walker.walkTreeFromRoot(directory)
				.filter(LocalFile::isFile)
				.collect(Collectors.toList())
				.parallelStream()
				.forEach(localFile -> uploadFileFrom(directory, localFile));
	}
}
