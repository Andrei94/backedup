package authentication;

import java.util.Date;

class CorrectCredentialsAuthenticator implements Authenticator {
	CorrectCredentialsAuthenticator() {
	}

	@Override
	public User authenticate(String username, String password) {
		return new AuthenticatedUser(username, new UserCredentials("accessKey", "secretKey", "sessionToken", new Date(new Date().getTime() + 12 * 3600 * 1000)));
	}
}