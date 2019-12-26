package uploader;

import authentication.User;

import java.nio.file.Path;

public interface ObjectUploader {
	boolean uploadDirectory(Path path);

	void setLoggedInUser(User user);
}
