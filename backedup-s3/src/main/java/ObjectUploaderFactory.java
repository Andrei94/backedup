public class ObjectUploaderFactory {
	public static ObjectUploader createS3ObjectUploader() {
		return new S3ObjectUploader(new S3Adapter(), new LocalFileWalker());
	}
}