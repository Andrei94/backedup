package authentication;

import drive.DriveGateway;

class LoginController {
	private Authenticator authenticator;

	LoginController() {
		this(new CognitoAuthenticator());
	}

	LoginController(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	LoginFeedback authenticate(String username, String password) {
		if(authenticator.authenticate(username, password)) {
			DriveGateway gateway = new DriveGateway();
			gateway.mount(username, password);
			return LoginFeedback.createSuccessfulLoginFeedback(gateway);
		} else {
			return LoginFeedback.createIncorrectCredentialsFeedback();
		}
	}
}