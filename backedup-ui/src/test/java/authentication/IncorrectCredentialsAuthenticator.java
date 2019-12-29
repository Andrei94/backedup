package authentication;

class IncorrectCredentialsAuthenticator implements Authenticator {
	IncorrectCredentialsAuthenticator() {
	}

	@Override
	public User authenticate(String username, String password) {
		return new UnauthenticatedUser(username);
	}

	@Override
	public User refresh(User user) {
		return new UnauthenticatedUser(user.getName());
	}
}
