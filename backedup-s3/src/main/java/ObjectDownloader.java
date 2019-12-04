public interface ObjectDownloader {
	boolean downloadDirectory(String remoteDir, String localDir);

	void setLoggedInUsername(String username);

	void shutdown();
}
