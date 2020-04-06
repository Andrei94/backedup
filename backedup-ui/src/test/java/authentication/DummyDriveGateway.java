package authentication;

import drive.DriveGateway;

public class DummyDriveGateway extends DriveGateway {
	@Override
	public String createRemoteDrive(String user) {
		return "token";
	}
}
