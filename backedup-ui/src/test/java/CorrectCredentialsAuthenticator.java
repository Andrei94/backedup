class CorrectCredentialsAuthenticator extends Authenticator {
	CorrectCredentialsAuthenticator() {
	}

	@Override
	boolean authenticate(String username, String password) {
		return true;
	}
}
