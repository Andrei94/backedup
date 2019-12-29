package sessionRefresher;

import authentication.User;

public interface SessionRefresher {
	User refresh(User refreshToken);
}
