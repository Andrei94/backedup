import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) {
		S3ObjectUploader uploader = new S3ObjectUploader(new S3Adapter());
		uploader.uploadDirectory(LocalFile.fromPath(Paths.get("D:\\Programming\\Intellij\\backedup\\src\\main")));
	}
}
