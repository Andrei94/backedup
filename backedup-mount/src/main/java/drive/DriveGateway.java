package drive;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriveGateway {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Process winssfs;

	public void mount(String username, String password) {
		mount(new MountArguments(
				"D:\\Programming\\Win-SSHFS\\Sshfs\\Sshfs\\bin\\Release\\WinSshFS.exe",
				"18.194.219.60",
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
