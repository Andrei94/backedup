package uploader;

import authentication.User;

import java.nio.file.Path;

public interface ObjectUploader {
	boolean uploadDirectory(Path path);

	void setLoggedInUser(User user);

	boolean uploadFile(Path file);

	void setHost(String host);
}
