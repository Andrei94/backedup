public class ObjectDownloaderFactory {
	public static ObjectDownloader createObjectDownloader() {
		return new S3ObjectDownloader(new S3Adapter());
	}
}
