package drive;

import com.google.gson.Gson;
import drive.adapters.HttpClient;
import drive.adapters.JsonSerializer;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriveGateway {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Process winsshfs;
	private HttpClient httpClient;
	private JsonSerializer jsonSerializer;

	public DriveGateway() {
		this(new JsonSerializer(new Gson()), new HttpClient(new OkHttpClient.Builder()
				.readTimeout(1, TimeUnit.MINUTES)
				.connectTimeout(1, TimeUnit.MINUTES)
				.build()));
	}

	public DriveGateway(JsonSerializer jsonSerializer, HttpClient httpClient) {
		this.jsonSerializer = jsonSerializer;
		this.httpClient = httpClient;
	}

	public void mountRemoteDrive(String username) {
		CreateUserDriveResponse remoteDrive = createRemoteDrive(username);
		mount(username, remoteDrive.getToken(), remoteDrive.getIp());
	}

	private CreateUserDriveResponse createRemoteDrive(String user) {
		logger.log(Level.INFO, "Creating remote drive for user " + user);
		return jsonSerializer.fromJson(
				httpClient.makePutRequest("https://i9bdhatjq3.execute-api.eu-central-1.amazonaws.com/test/volume", jsonSerializer.toJson(new CreateUserDriveRequest(user))),
				CreateUserDriveResponse.class
		);
	}

	private void mount(String username, String password, String ip) {
		mount(new MountArguments(
				"D:\\Programming\\Win-SSHFS\\Sshfs\\Sshfs\\bin\\Release\\WinSshFS.exe",
				ip,
				"22",
				username,
				"files",
				username, password));
	}

	private void mount(MountArguments arguments) {
		try {
			logger.info("Performing mount of drive");
			winsshfs = new ProcessBuilder(arguments.toCommandWithArguments()).start();
			logger.info("Mount performed");
		} catch(IOException e) {
			logger.log(Level.SEVERE, "Failed to mount drive", e);
		}
	}

	public void unmount() {
		if(winsshfs != null) {
			winsshfs.destroy();
			logger.info("Drive unmounted");
		}
	}
}
