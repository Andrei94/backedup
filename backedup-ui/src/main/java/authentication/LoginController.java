package authentication;

import downloader.ObjectDownloader;
import downloader.ObjectDownloaderFactory;
import drive.DriveGateway;

import java.io.File;

class LoginController {
	private Authenticator authenticator;
	private User user;

	LoginController() {
		this(new CognitoAuthenticator());
	}

	LoginController(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	LoginFeedback authenticate(String username, String password) {
		user = authenticator.authenticate(username, password);
		if(user.isAuthenticated()) {
			DriveGateway gateway = new DriveGateway();
			gateway.createRemoteDrive(username);
			gateway.mount(username, password);
			if(!new File("list.txt").exists()) {
				ObjectDownloader objectDownloader = ObjectDownloaderFactory.createObjectDownloader(user);
				objectDownloader.setLoggedInUser(user);
				objectDownloader.downloadFolderList();
				return LoginFeedback.createSuccessfulLoginFeedback(gateway, objectDownloader);
			}
			return LoginFeedback.createSuccessfulLoginFeedback(gateway);
		} else {
			return LoginFeedback.createIncorrectCredentialsFeedback();
		}
	}

	public User getUser() {
		return user;
	}
}