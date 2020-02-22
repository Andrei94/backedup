package dashboard;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class UploadFolderListWorker extends Service<Boolean> {
	private DashboardController controller;

	UploadFolderListWorker(DashboardController controller) {
		this.controller = controller;
	}

	@Override
	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {
			@Override
			protected Boolean call() {
				boolean upload = controller.uploadFolderList();
				if(!upload)
					throw new RuntimeException();
				return true;
			}
		};
	}
}
