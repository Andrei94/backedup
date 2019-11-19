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
	private LoginWorker loginWorker = new LoginWorker(controller);

	public void login(ActionEvent actionEvent) {
		loginWorker = loginWorker.bindUsername(username.textProperty())
				.bindPassword(password.textProperty());
		loginIndicator.visibleProperty().bind(loginWorker.runningProperty());
		loginWorker.setOnSucceeded(event -> showLoginCallFeedback());
		loginWorker.restart();
	}

	private void showLoginCallFeedback() {
		LoginFeedback l = loginWorker.getValue();
		if(l.isSuccessfulLogin())
			close(new ActionEvent());
		else {
			loginResult.setText(l.getText());
			loginResult.setFill(l.getColor());
		}
	}

	public void close(ActionEvent actionEvent) {
		((Stage) username.getScene().getWindow()).close();
	}
}