package authentication;

import java.util.Optional;

class UnauthenticatedUser implements User {
	private String name;

	public UnauthenticatedUser(String name) {
		this.name = name;
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Optional<UserCredentials> getCredentials() {
		return Optional.empty();
	}

	@Override
	public String getRefreshToken() {
		return "";
	}
}
