package dashboard;

import authentication.User;
import uploader.ObjectUploader;

import java.nio.file.Path;

class S3UploaderMock implements ObjectUploader {
	private int timesUploadDirectoryCalled = 0;
	private User loggedInUser;

	@Override
	public boolean uploadDirectory(Path directory) {
		if(loggedInUser == null || !loggedInUser.isAuthenticated())
			return false;
		timesUploadDirectoryCalled++;
		return true;
	}

	@Override
	public void setLoggedInUser(User user) {
		this.loggedInUser = user;
	}

	int getTimesUploadDirectoryCalled() {
		return timesUploadDirectoryCalled;
	}
}
