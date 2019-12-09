package dashboard;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class UploadDirectoryWorker extends Service<Boolean> {
	private DashboardController controller;
	private FolderProgressMediator mediator;

	UploadDirectoryWorker(DashboardController controller, FolderProgressMediator mediator) {
		this.controller = controller;
		this.mediator = mediator;
	}

	@Override
	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {
			@Override
			protected Boolean call() {
				return controller.upload(mediator.getFolder());
			}
		};
	}

	@Override
	protected void running() {
		mediator.update(controller.getWIPImageUrl());
	}

	@Override
	protected void succeeded() {
		mediator.update(controller.getSucceededImageUrl());
	}
}
