package dashboard;

import uploader.ObjectUploader;

import java.nio.file.Path;

class S3UploaderMock implements ObjectUploader {
	private int timesUploadDirectoryCalled = 0;
	private String loggedInUsername;

	@Override
	public boolean uploadDirectory(Path directory) {
		if(loggedInUsername == null || loggedInUsername.isEmpty())
			return false;
		timesUploadDirectoryCalled++;
		return true;
	}

	@Override
	public void setLoggedInUsername(String username) {
		this.loggedInUsername = username;
	}

	int getTimesUploadDirectoryCalled() {
		return timesUploadDirectoryCalled;
	}
}
