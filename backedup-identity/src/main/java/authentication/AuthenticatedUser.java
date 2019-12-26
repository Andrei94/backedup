package authentication;

import java.util.Date;
import java.util.Optional;

public class AuthenticatedUser implements User {
	private String name;
	private UserCredentials keys;

	public AuthenticatedUser(String name, UserCredentials keys) {
		this.name = name;
		this.keys = keys;
	}

	@Override
	public boolean isAuthenticated() {
		return keys != null && keys.getExpiration().after(new Date());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Optional<UserCredentials> getCredentials() {
		return Optional.of(keys);
	}
}
