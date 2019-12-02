public class S3DownloaderMock implements ObjectDownloader {
	private String loggedInUsername;

	@Override
	public boolean downloadDirectory(String remoteDir, String localDir) {
		return loggedInUsername != null && !loggedInUsername.isEmpty();
	}

	@Override
	public void setLoggedInUsername(String username) {
		this.loggedInUsername = username;
	}
}
