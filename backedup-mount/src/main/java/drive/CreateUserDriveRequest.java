package drive;

public class CreateUserDriveRequest {
	private String username;

	public CreateUserDriveRequest(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}