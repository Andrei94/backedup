package uploader;

import adapter.S3Adapter;
import authentication.User;

public class ObjectUploaderFactory {
	public static ObjectUploader createS3ObjectUploader(User loggedInUser) {
		if(loggedInUser.isAuthenticated() && loggedInUser.getCredentials().isPresent())
			return new S3ObjectUploader(new S3Adapter(loggedInUser.getCredentials().get()), new LocalFileWalker());
		throw new IllegalArgumentException();
	}
}
