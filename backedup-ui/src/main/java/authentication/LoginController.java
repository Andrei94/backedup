package authentication;

class LoginController {
	private Authenticator authenticator;

	LoginController() {
		this(new CognitoAuthenticator());
	}

	LoginController(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	LoginFeedback authenticate(String username, String password) {
		return authenticator.authenticate(username, password) ?
				LoginFeedback.createSuccessfulLoginFeedback() :
				LoginFeedback.createIncorrectCredentialsFeedback();
	}
}