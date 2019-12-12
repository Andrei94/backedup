package dashboard;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class DownloadDirectoryWorker extends Service<Boolean> {
	private final DashboardController controller;
	private final FolderProgressMediator mediator;

	public DownloadDirectoryWorker(DashboardController controller, FolderProgressMediator mediator) {
		this.controller = controller;
		this.mediator = mediator;
	}

	@Override
	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {
			@Override
			protected Boolean call() {
				boolean download = controller.download(mediator.getFolder());
				if(!download)
					throw new RuntimeException();
				return true;
			}
		};
	}

	@Override
	protected void running() {
		mediator.update(controller.getWIPImageUrl());
	}

	@Override
	protected void failed() {
		mediator.update(controller.getFailedImageUrl());
	}

	@Override
	protected void succeeded() {
		mediator.update(controller.getSucceededImageUrl());
	}
}
