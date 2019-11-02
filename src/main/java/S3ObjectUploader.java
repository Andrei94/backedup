import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class S3ObjectUploader {
	private final S3Adapter adapter;

	public S3ObjectUploader(S3Adapter adapter) {
		this.adapter = adapter;
	}

	boolean upload(String remoteFolder, LocalFile localFile) {
		return adapter.putObject(new UploadObjectRequest()
				.withBucket("backedup-storage")
				.withRemoteFile(adapter.toFileInRemoteFolder(remoteFolder, localFile.getPath()))
				.withLocalFile(localFile)
				.withStorageClass("GLACIER")
				.toS3PutObjectRequest());
	}

	void uploadDirectory(LocalPath root) {
		walkTreeFromRoot(root)
				.filter(LocalPath::isFile)
				.parallel()
				.forEach(localPath -> adapter.putObject(new UploadObjectRequest()
						.withBucket("backedup-storage")
						.withRemoteFile(adapter.toFileInRemoteFolder(root.getFilename(), root.relativize(localPath)))
						.withLocalFile(localPath.getLocalFile())
						.withStorageClass("STANDARD")
						.toS3PutObjectRequest()));
	}

	protected Stream<LocalPath> walkTreeFromRoot(LocalPath root) {
		try {
			return Files.walk(root.toPath()).map(LocalPath::fromPath);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return Stream.empty();
	}
}
