class CorrectCredentialsAuthenticator implements Authenticator {
	CorrectCredentialsAuthenticator() {
	}

	@Override
	public boolean authenticate(String username, String password) {
		return true;
	}
}
