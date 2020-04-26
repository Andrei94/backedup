package dashboard;

import drive.DriveGateway;
import drive.adapters.HttpClient;
import drive.adapters.JsonSerializer;

public class DriveGatewayUserNotLoggedIn extends DriveGateway {
	public DriveGatewayUserNotLoggedIn() {
		this(null, null);
	}

	private DriveGatewayUserNotLoggedIn(JsonSerializer jsonSerializer, HttpClient httpClient) {
	}

	@Override
	public String getIp() {
		return null;
	}
}
