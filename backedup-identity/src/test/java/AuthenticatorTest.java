import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticatorTest {
	private Authenticator authe = new Authenticator(new IdentityProviderWithAuthParametersChecker());

	@Test
	void authenticationSuccess() {
		assertTrue(authe.authenticate("username", "Password123!"));
	}

	@Test
	void authenticationFailed() {
		assertFalse(authe.authenticate("username", "password!"));
	}

	@Test
	void failedAuthenticationWithInnexistentUser() {
		assertFalse(authe.authenticate("user", "password!"));
	}
}
