import javafx.scene.paint.Color;

class LoginController {
	private Authenticator authenticator = new Authenticator();

	boolean authenticate(String username, String password) {
		return authenticator.authenticate(username, password);
	}

	String getIncorrectCredentialsMessage() {
		return "Incorrect credentials";
	}

	Color getIncorrectCredentialsColor() {
		return Color.RED;
	}
}
