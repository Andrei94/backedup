package sessionRefresher;

import authentication.Authenticator;
import authentication.CognitoAuthenticator;
import authentication.User;

public class AssumedRoleRefresher implements SessionRefresher {
	private Authenticator authenticator;

	public AssumedRoleRefresher() {
		this(new CognitoAuthenticator());
	}

	AssumedRoleRefresher(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	@Override
	public User refresh(User user) {
		return authenticator.refresh(user);
	}
}
