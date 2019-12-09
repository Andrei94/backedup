package authentication;

class IncorrectCredentialsAuthenticator implements Authenticator {
	IncorrectCredentialsAuthenticator() {
	}

	@Override
	public boolean authenticate(String username, String password) {
		return false;
	}
}
