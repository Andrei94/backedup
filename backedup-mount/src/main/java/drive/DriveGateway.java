package drive;

import drive.adapters.HttpClient;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriveGateway {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Process winssfs;
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

	public void createRemoteDrive(String user) {
		logger.log(Level.INFO, "Creating remote drive for user " + user);
		httpClient.makePutRequest("http://192.168.232.128:8080/volume/" + user, "");
	}

	public void mount(String username, String password) {
		mount(new MountArguments(
				"D:\\Programming\\Win-SSHFS\\Sshfs\\Sshfs\\bin\\Release\\WinSshFS.exe",
				"192.168.232.128",
				"22",
				username,
				"files",
				username, password));
	}

	private void mount(MountArguments arguments) {
		try {
			logger.info("Performing mount of drive");
			winssfs = new ProcessBuilder(arguments.toCommandWithArguments()).start();
			logger.info("Mount performed");
		} catch(IOException e) {
			logger.log(Level.SEVERE, "Failed to mount drive", e);
		}
	}

	public void unmount() {
		if(winssfs != null) {
			winssfs.destroy();
			logger.info("Drive unmounted");
		}
	}
}
