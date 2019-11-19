import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

class LoginWorker extends Service<LoginFeedback> {
	private final LoginController controller;
	private final SimpleStringProperty username;
	private final SimpleStringProperty password;

	LoginWorker(LoginController controller) {
		this.controller = controller;
		this.username = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
	}

	@Override
	protected Task<LoginFeedback> createTask() {
		return new Task<LoginFeedback>() {
			@Override
			protected LoginFeedback call() {
				return controller.authenticate(username.getValue(), password.getValue());
			}
		};
	}

	LoginWorker bindUsername(StringProperty username) {
		this.username.bind(username);
		return this;
	}

	LoginWorker bindPassword(StringProperty password) {
		this.password.bind(password);
		return this;
	}
}
