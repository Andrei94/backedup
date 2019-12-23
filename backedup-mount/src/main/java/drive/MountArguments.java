package drive;

import java.util.Arrays;
import java.util.List;

public class MountArguments {
	private String mountApplication;
	private String host;
	private String mountName;
	private String username;
	private String password;

	public MountArguments(String mountApplication, String host, String mountName, String username, String password) {
		this.mountApplication = mountApplication;
		this.host = host;
		this.mountName = mountName;
		this.username = username;
		this.password = password;
	}

	public List<String> toCommandWithArguments() {
		return Arrays.asList(mountApplication, mountName, host, username, password);
	}
}
