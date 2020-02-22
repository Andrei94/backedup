package authentication;

import downloader.ObjectDownloader;
import drive.DriveGateway;
import javafx.scene.paint.Color;

class LoginFeedback {
	private final String message;
	private final Color color;
	private DriveGateway driveGateway;
	private ObjectDownloader objectDownloader;

	private LoginFeedback(String message, Color messageColor, DriveGateway driveGateway, ObjectDownloader objectDownloader) {
		this.message = message;
		this.color = messageColor;
		this.driveGateway = driveGateway;
		this.objectDownloader = objectDownloader;
	}

	private LoginFeedback(String message, Color messageColor, DriveGateway driveGateway) {
		this.message = message;
		this.color = messageColor;
		this.driveGateway = driveGateway;
		this.objectDownloader = null;
	}

	static LoginFeedback createIncorrectCredentialsFeedback() {
		return new LoginFeedback("Incorrect credentials", Color.RED, null, null);
	}

	static LoginFeedback createSuccessfulLoginFeedback(DriveGateway gateway, ObjectDownloader objectDownloader) {
		return new LoginFeedback("", Color.GREEN, gateway, objectDownloader);
	}

	static LoginFeedback createSuccessfulLoginFeedback(DriveGateway gateway) {
		return new LoginFeedback("", Color.GREEN, gateway);
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

	DriveGateway getDriveGateway() {
		return driveGateway;
	}

	ObjectDownloader getObjectDownloader() {
		return objectDownloader;
	}
}
