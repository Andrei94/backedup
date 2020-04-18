package dashboard;

import drive.SubscriptionSpace;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class ShowSubscriptionService extends ScheduledService<SubscriptionSpace> {
	private final DashboardController controller;

	public ShowSubscriptionService(DashboardController controller) {
		this.controller = controller;
		this.setPeriod(Duration.minutes(1));
		this.setRestartOnFailure(true);
	}

	@Override
	protected Task<SubscriptionSpace> createTask() {
		return new Task<SubscriptionSpace>() {
			@Override
			protected SubscriptionSpace call() {
				return controller.showSubscriptionSpace();
			}
		};
	}
}
