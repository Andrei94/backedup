package drive;

import java.util.Arrays;
import java.util.List;

public class MountArguments {
	private String mountApplication;
	private String host;
	private String port;
	private String rootFolder;
	private String mountName;
	private String username;
	private String password;

	public MountArguments(String mountApplication, String host, String port, String rootFolder, String mountName, String username, String password) {
		this.mountApplication = mountApplication;
		this.host = host;
		this.port = port;
		this.rootFolder = rootFolder;
		this.mountName = mountName;
		this.username = username;
		this.password = password;
	}

	public List<String> toCommandWithArguments() {
		return Arrays.asList(mountApplication, mountName, port, rootFolder, host, username, password);
	}
}
