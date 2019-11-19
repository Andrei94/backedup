import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

class LoginService extends Service<Boolean> {
	private final LoginController controller;
	private final SimpleStringProperty username;
	private final SimpleStringProperty password;

	LoginService(LoginController controller) {
		this.controller = controller;
		this.username = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
	}

	@Override
	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {
			@Override
			protected Boolean call() {
				return controller.authenticate(username.getValue(), password.getValue());
			}
		};
	}

	LoginService bindUsername(StringProperty username) {
		this.username.bind(username);
		return this;
	}

	LoginService bindPassword(StringProperty password) {
		this.password.bind(password);
		return this;
	}
}
