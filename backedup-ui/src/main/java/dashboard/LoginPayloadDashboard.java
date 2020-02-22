package dashboard;

import authentication.User;
import downloader.ObjectDownloader;
import drive.DriveGateway;

public class LoginPayloadDashboard {
	private User loggedInUser;
	private DriveGateway driveGateway;
	private final ObjectDownloader objectDownloader;

	public LoginPayloadDashboard(User loggedInUser, DriveGateway driveGateway, ObjectDownloader objectDownloader) {
		this.loggedInUser = loggedInUser;
		this.driveGateway = driveGateway;
		this.objectDownloader = objectDownloader;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public DriveGateway getDriveGateway() {
		return driveGateway;
	}

	public ObjectDownloader getObjectDownloader() {
		return objectDownloader;
	}
}
