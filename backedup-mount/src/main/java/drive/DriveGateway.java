package drive;

import drive.adapters.HttpClient;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriveGateway {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Process winsshfs;
	private HttpClient httpClient;

	public DriveGateway() {
		this(new HttpClient(new OkHttpClient.Builder()
				.readTimeout(30, TimeUnit.SECONDS)
				.connectTimeout(30, TimeUnit.SECONDS)
				.build()));
	}

	public DriveGateway(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void mountRemoteDrive(String username) {
		mount(username, createRemoteDrive(username));
	}

	public String createRemoteDrive(String user) {
		logger.log(Level.INFO, "Creating remote drive for user " + user);
		return httpClient.makePutRequest("http://35.158.140.9:8080/volume/" + user, "").split(" ")[1];
	}

	public void mount(String username, String password) {
		mount(new MountArguments(
				"D:\\Programming\\Win-SSHFS\\Sshfs\\Sshfs\\bin\\Release\\WinSshFS.exe",
				"35.158.140.9",
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
