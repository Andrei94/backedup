import javafx.scene.paint.Color;

class LoginFeedback {
	private final String message;
	private final Color color;

	private LoginFeedback(String message, Color messageColor) {
		this.message = message;
		this.color = messageColor;
	}

	static LoginFeedback createIncorrectCredentialsFeedback() {
		return new LoginFeedback("Incorrect credentials", Color.RED);
	}

	static LoginFeedback createSuccessfulLoginFeedback() {
		return new LoginFeedback("", Color.GREEN);
	}

	boolean isSuccessfulLogin() {
		return message.isEmpty() && color.equals(Color.GREEN);
	}

	String getText() {
		return message;
	}

	Color getColor() {
		return color;
	}
}
