package authentication;

import java.util.Date;
import java.util.Optional;

public class AuthenticatedUser implements User {
	private String name;
	private UserCredentials keys;
	private final String refreshToken;

	public AuthenticatedUser(String name, UserCredentials keys, String refreshToken) {
		this.name = name;
		this.keys = keys;
		this.refreshToken = refreshToken;
	}

	@Override
	public boolean isAuthenticated() {
		return Optional.ofNullable(keys).isPresent() && keys.getExpiration().after(new Date());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Optional<UserCredentials> getCredentials() {
		return Optional.of(keys);
	}

	@Override
	public String getRefreshToken() {
		return refreshToken;
	}
}
