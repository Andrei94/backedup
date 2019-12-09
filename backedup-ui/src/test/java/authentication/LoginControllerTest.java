package authentication;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {
	@Test
	void authenticateWithIncorrectCredentials() {
		LoginController controller = new LoginController(new IncorrectCredentialsAuthenticator());
		LoginFeedback feedback = controller.authenticate("username", "password");
		assertAll(
				() -> assertEquals("Incorrect credentials", feedback.getText()),
				() -> assertEquals(Color.RED, feedback.getColor())
		);
	}

	@Test
	void authenticateWithCorrectCredentials() {
		LoginController controller = new LoginController(new CorrectCredentialsAuthenticator());
		LoginFeedback feedback = controller.authenticate("username", "Password123!");
		assertTrue(feedback.isSuccessfulLogin());
	}
}