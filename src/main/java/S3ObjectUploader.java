import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
				.withStorageClass("STANDARD")
				.toS3PutObjectRequest());
	}

	void uploadDirectory(LocalFile directory) {
		walkTreeFromRoot(directory)
				.filter(LocalFile::isFile)
				.parallel()
				.forEach(localFile -> adapter.putObject(new UploadObjectRequest()
						.withBucket("backedup-storage")
						.withRemoteFile(adapter.toFileInRemoteFolder(directory.getName(), directory.relativize(localFile)))
						.withLocalFile(localFile)
						.withStorageClass("STANDARD")
						.toS3PutObjectRequest()));
	}

	protected Stream<LocalFile> walkTreeFromRoot(LocalFile root) {
		try {
			return Files.walk(Paths.get(root.getPath())).map(path -> LocalFile.fromFile(path.toFile()));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return Stream.empty();
	}
}
