package dashboard;

import authentication.User;
import drive.DriveGateway;

public class LoginPayloadDashboard {
	private User loggedInUser;
	private DriveGateway driveGateway;

	public LoginPayloadDashboard(User loggedInUser, DriveGateway driveGateway) {
		this.loggedInUser = loggedInUser;
		this.driveGateway = driveGateway;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public DriveGateway getDriveGateway() {
		return driveGateway;
	}
}
