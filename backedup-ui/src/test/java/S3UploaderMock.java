import java.nio.file.Path;

class S3UploaderMock implements ObjectUploader {
	private int timesUploadDirectoryCalled = 0;
	private String loggedInUsername;

	@Override
	public void uploadDirectory(Path directory) {
		if(loggedInUsername == null || loggedInUsername.isEmpty())
			return;
		timesUploadDirectoryCalled++;
	}

	@Override
	public void setLoggedInUsername(String username) {
		this.loggedInUsername = username;
	}

	int getTimesUploadDirectoryCalled() {
		return timesUploadDirectoryCalled;
	}
}
