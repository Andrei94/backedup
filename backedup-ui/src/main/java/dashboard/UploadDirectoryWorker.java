package dashboard;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

public class UploadDirectoryWorker extends Service<Boolean> {
	private DashboardController controller;
	private List<FolderProgressMediator> mediators;

	UploadDirectoryWorker(DashboardController controller, List<FolderProgressMediator> mediators) {
		this.controller = controller;
		this.mediators = mediators;
	}

	@Override
	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {
			@Override
			protected Boolean call() {
				List<Folder> syncList = controller.getSyncList();
				for(int i = 0; i < syncList.size(); i++) {
					mediators.get(i).update(controller.getWIPImageUrl());
					if(controller.upload(syncList.get(i)))
						mediators.get(i).update(controller.getSucceededImageUrl());
					else
						mediators.get(i).update(controller.getFailedImageUrl());
				}
				return true;
			}
		};
	}
}
