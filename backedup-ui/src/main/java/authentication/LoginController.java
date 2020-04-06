package authentication;

import downloader.ObjectDownloader;
import downloader.ObjectDownloaderFactory;
import drive.DriveGateway;

import java.io.File;

class LoginController {
	private DriveGateway driveGateway;
	private Authenticator authenticator;
	private User user;

	LoginController() {
		this(new CognitoAuthenticator(), new DriveGateway());
	}

	LoginController(Authenticator authenticator, DriveGateway driveGateway) {
		this.authenticator = authenticator;
		this.driveGateway = driveGateway;
	}

	LoginFeedback authenticate(String username, String password) {
		user = authenticator.authenticate(username, password);
		if(user.isAuthenticated()) {
			driveGateway.mountRemoteDrive(username);
			if(!new File("list.txt").exists()) {
				ObjectDownloader objectDownloader = ObjectDownloaderFactory.createObjectDownloader(user);
				objectDownloader.setLoggedInUser(user);
				objectDownloader.downloadFolderList();
				return LoginFeedback.createSuccessfulLoginFeedback(driveGateway, objectDownloader);
			}
			return LoginFeedback.createSuccessfulLoginFeedback(driveGateway);
		} else {
			return LoginFeedback.createIncorrectCredentialsFeedback();
		}
	}

	public User getUser() {
		return user;
	}
}