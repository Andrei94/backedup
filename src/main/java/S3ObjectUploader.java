public class S3ObjectUploader {
	private final S3Adapter adapter;
	private final LocalFileWalker walker;

	public S3ObjectUploader(S3Adapter adapter, LocalFileWalker walker) {
		this.adapter = adapter;
		this.walker = walker;
	}

	boolean uploadFileFrom(LocalFile directory, LocalFile localFile) {
		return adapter.putObject(new UploadObjectRequest()
				.withBucket("backedup-storage")
				.withRemoteFile(adapter.toFileInRemoteFolder(directory.getName(), directory.relativize(localFile)))
				.withLocalFile(localFile)
				.withStorageClass("STANDARD"));
	}

	void uploadDirectory(LocalFile directory) {
		walker.walkTreeFromRoot(directory)
				.filter(LocalFile::isFile)
				.parallel()
				.forEach(localFile -> uploadFileFrom(directory, localFile));
	}
}
