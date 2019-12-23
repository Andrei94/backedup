package drive;

import java.io.IOException;

public class DriveGateway {
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
			winssfs = new ProcessBuilder(arguments.toCommandWithArguments()).start();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void unmount() {
		if(winssfs != null)
			winssfs.destroy();
	}
}
