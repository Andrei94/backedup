package authentication;

import drive.DriveGateway;

class LoginController {
	private Authenticator authenticator;
	private User user;

	LoginController() {
		this(new CognitoAuthenticator());
	}

	LoginController(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	LoginFeedback authenticate(String username, String password) {
		user = authenticator.authenticate(username, password);
		if(user.isAuthenticated()) {
			DriveGateway gateway = new DriveGateway();
			gateway.mount(username, password);
			return LoginFeedback.createSuccessfulLoginFeedback(gateway);
		} else {
			return LoginFeedback.createIncorrectCredentialsFeedback();
		}
	}

	public User getUser() {
		return user;
	}
}