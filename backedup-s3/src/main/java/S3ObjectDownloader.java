public class S3ObjectDownloader {
	private S3Adapter adapter = new S3Adapter();

	boolean downloadDirectory(String remoteDir, LocalFile localDir) {
		return adapter.downloadDirectory(remoteDir, localDir.getPath());
	}
}
