import authentication.CognitoAuthenticator;
import authentication.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CognitoAuthenticatorTest {
	private CognitoAuthenticator authe = new CognitoAuthenticator(
			new IdentityProviderWithAuthParametersChecker(),
			new AmazonCognitoIdentityProviderStub(),
			new AWSSecurityTokenServiceStub()
	);

	@Test
	void authenticationSuccess() {
		User user = authe.authenticate("username", "Password123!");
		assertEquals("username", user.getName());
		assertAuthenticated(user);
	}

	private void assertAuthenticated(User user) {
		assertTrue(user.isAuthenticated());
		assertTrue(user.getCredentials().isPresent());
		assertEquals("accessKeyId", user.getCredentials().get().getAccessKeyId());
		assertEquals("secretKey", user.getCredentials().get().getSecretAccessKey());
		assertEquals("sessionToken", user.getCredentials().get().getSessionToken());
	}

	@Test
	void authenticationFailed() {
		User user = authe.authenticate("username", "password!");
		assertEquals("username", user.getName());
		assertNotAuthenticatedUser(user);
	}

	@Test
	void failedAuthenticationWithInnexistentUser() {
		User user = authe.authenticate("user", "password!");
		assertEquals("user", user.getName());
		assertNotAuthenticatedUser(user);
	}

	private void assertNotAuthenticatedUser(User user) {
		assertFalse(user.isAuthenticated());
		assertFalse(user.getCredentials().isPresent());
	}
}
