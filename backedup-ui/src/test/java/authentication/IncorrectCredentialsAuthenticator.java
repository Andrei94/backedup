package authentication;

class IncorrectCredentialsAuthenticator implements Authenticator {
	IncorrectCredentialsAuthenticator() {
	}

	@Override
	public User authenticate(String username, String password) {
		return new UnauthenticadUser(username);
	}
}
