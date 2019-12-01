public class S3ObjectDownloader {
	private S3Adapter adapter;

	public S3ObjectDownloader(S3Adapter adapter) {
		this.adapter = adapter;
	}

	boolean downloadDirectory(String remoteDir, LocalFile localDir) {
		return adapter.downloadDirectoryExcludingGlacier(remoteDir, localDir.getPath());
	}
}
