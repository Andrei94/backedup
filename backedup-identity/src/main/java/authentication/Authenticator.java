package authentication;

public interface Authenticator {
	User authenticate(String username, String password);
}
