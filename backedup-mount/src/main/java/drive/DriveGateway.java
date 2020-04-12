package drive;

import com.google.gson.Gson;
import drive.adapters.HttpClient;
import drive.adapters.JsonSerializer;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
				.readTimeout(30, TimeUnit.SECONDS)
				.connectTimeout(30, TimeUnit.SECONDS)
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
		logger.log(Level.INFO, "Creating user " + user);
		CreateUserResponse createUserResponse = jsonSerializer.fromJson(
				httpClient.makePutRequest("https://i9bdhatjq3.execute-api.eu-central-1.amazonaws.com/test/volume/createuser",
						jsonSerializer.toJson(new CreateUserRequest(user))),
				CreateUserResponse.class
		);
		if(!createUserResponse.getToken().isEmpty()) {
			logger.log(Level.INFO, "User " + user + " created");
			logger.log(Level.INFO, "Creating remote drive" + user);
			CreateUserDriveResponse createUserDriveResponse = jsonSerializer.fromJson(
					httpClient.makePutRequest("https://i9bdhatjq3.execute-api.eu-central-1.amazonaws.com/test/volume",
							jsonSerializer.toJson(new CreateUserDriveRequest(user, sha512(createUserResponse.getToken())))
					),
					CreateUserDriveResponse.class
			);
			logger.log(Level.INFO, "Remote drive created");
			return createUserDriveResponse;
		} else {
			logger.log(Level.WARNING, "Failed to create user " + user);
			throw new RuntimeException();
		}
	}

	private String sha512(String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			return new String(digest.digest(text.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
		} catch(NoSuchAlgorithmException e) {
			logger.log(Level.WARNING, "Failed to initialize hash algorithm");
		}
		return "";
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
