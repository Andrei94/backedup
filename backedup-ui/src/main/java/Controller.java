import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Controller {
	public TreeView<TreeViewFile> treeView;

	public void openFileBrowser(MouseEvent mouseEvent) throws IOException {
		File file = new DirectoryChooser().showDialog(treeView.getScene().getWindow());
		if(file == null)
			return;
		TreeItem<TreeViewFile> root = new TreeItem<>(new TreeViewFile(file.getName(), file.toPath()));
		createTree(root);
		appendRootToTreeView(root);
	}

	private void appendRootToTreeView(TreeItem<TreeViewFile> root) {
		if(treeView.getRoot() == null)
			treeView.setRoot(root);
		else {
			TreeItem<TreeViewFile> objectTreeItem = new TreeItem<>();
			objectTreeItem.getChildren().add(treeView.getRoot());
			objectTreeItem.getChildren().add(root);
			treeView.setRoot(objectTreeItem);
			treeView.setShowRoot(false);
		}
	}

	private void createTree(TreeItem<TreeViewFile> rootItem) throws IOException {
		try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootItem.getValue().path)) {
			for(Path path : directoryStream) {
				TreeItem<TreeViewFile> newItem = new TreeItem<>(new TreeViewFile(path.toFile().getName(), path));
				newItem.setExpanded(false);

				rootItem.getChildren().add(newItem);

				if(path.toFile().isDirectory()) {
					createTree(newItem);
				}
			}
		}
	}

	public void deleteTreeItemWithConfirmation(MouseEvent mouseEvent) {
		TreeItem<TreeViewFile> selectedItem = treeView.getSelectionModel().getSelectedItem();
		if(selectedItem == null)
			return;
		if(selectedItem.getParent() == null)
			treeView.setRoot(null);
		else
			selectedItem.getParent().getChildren().remove(selectedItem);
	}
}