package dashboard;

import drive.DriveGateway;
import drive.adapters.HttpClient;
import drive.adapters.JsonSerializer;

public class DriveGatewayMock extends DriveGateway {
	public DriveGatewayMock() {
		this(null, null);
	}

	private DriveGatewayMock(JsonSerializer jsonSerializer, HttpClient httpClient) {
	}

	@Override
	public String getIp() {
		return "127.0.0.1";
	}
}
