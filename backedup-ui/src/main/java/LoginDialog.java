import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginDialog {
	public TextField username;
	public PasswordField password;
	public ProgressIndicator loginIndicator;
	public Text loginResult;

	private LoginController controller = new LoginController();
	private LoginService loginService = new LoginService(controller);

	public void login(ActionEvent actionEvent) {
		loginService = loginService.bindUsername(username.textProperty())
				.bindPassword(password.textProperty());
		loginIndicator.visibleProperty().bind(loginService.runningProperty());
		loginService.setOnSucceeded(event -> showLoginCallFeedback());
		loginService.restart();
	}

	private void showLoginCallFeedback() {
		if(loginService.getValue())
			close(null);
		else {
			loginResult.setText(controller.getIncorrectCredentialsMessage());
			loginResult.setFill(controller.getIncorrectCredentialsColor());
		}
	}

	public void close(ActionEvent actionEvent) {
		((Stage) username.getScene().getWindow()).close();
	}
}