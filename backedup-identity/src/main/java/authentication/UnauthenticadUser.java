package authentication;

import java.util.Optional;

class UnauthenticadUser implements User {
	private String name;

	public UnauthenticadUser(String name) {
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
}
