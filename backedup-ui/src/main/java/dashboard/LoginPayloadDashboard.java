package dashboard;

import drive.DriveGateway;

public class LoginPayloadDashboard {
	private String loggedInUsername;
	private DriveGateway driveGateway;

	public LoginPayloadDashboard(String loggedInUsername, DriveGateway driveGateway) {
		this.loggedInUsername = loggedInUsername;
		this.driveGateway = driveGateway;
	}

	public String getLoggedInUsername() {
		return loggedInUsername;
	}

	public DriveGateway getDriveGateway() {
		return driveGateway;
	}
}
