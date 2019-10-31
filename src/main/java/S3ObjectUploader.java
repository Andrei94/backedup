import java.io.File;
import java.nio.file.Paths;

public class S3ObjectUploader {
	private final S3Adapter adapter;

	public S3ObjectUploader(S3Adapter adapter) {
		this.adapter = adapter;
	}

	boolean upload(String remoteFolder, File localFile) {
		return adapter.putObject(new UploadObjectRequest()
				.withBucket("backedup-storage")
				.withRemoteFile(adapter.toFileInRemoteFolder(remoteFolder, localFile))
				.withLocalFile(localFile)
				.withStorageClass("GLACIER")
				.toS3PutObjectRequest());
	}

	public static void main(String[] args) {
		S3ObjectUploader uploader = new S3ObjectUploader(new S3Adapter());
		uploader.upload("Cities.Skylines.Demo",
				Paths.get("D:\\Programming\\Intellij\\backedup\\src\\main\\resources\\Cities.Skylines.Demo\\file1").toFile());
		uploader.upload("Cities.Skylines.Demo",
				Paths.get("D:\\Programming\\Intellij\\backedup\\src\\main\\resources\\Cities.Skylines.Demo\\file2").toFile());
	}
}
