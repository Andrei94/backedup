package authentication;

import java.util.Optional;

public interface User {
	boolean isAuthenticated();

	String getName();

	Optional<UserCredentials> getCredentials();

	String getRefreshToken();
}
