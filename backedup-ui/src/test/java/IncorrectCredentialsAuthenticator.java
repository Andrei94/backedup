class IncorrectCredentialsAuthenticator extends Authenticator {
	IncorrectCredentialsAuthenticator() {
	}

	@Override
	boolean authenticate(String username, String password) {
		return false;
	}
}
