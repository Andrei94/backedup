package downloader;

import adapter.S3Adapter;
import authentication.User;

public class ObjectDownloaderFactory {
	public static ObjectDownloader createObjectDownloader(User loggedInUser) {
		if(loggedInUser.isAuthenticated() && loggedInUser.getCredentials().isPresent())
			return new S3ObjectDownloader(new S3Adapter(loggedInUser.getCredentials().get()));
		throw new IllegalArgumentException();
	}
}
