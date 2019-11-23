import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class NewMainWindow implements Initializable {
	public TilePane contents;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			String url = new File("D:\\Programming\\Intellij\\backedup\\backedup-ui\\src\\main\\resources\\folder_80px.png").toURI().toURL().toExternalForm();
			addContents(new Image(url, 80, 80, true, true));
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void addContents(Image image) {
		TileDecorator decorator = new TileDecorator();
		contents.getChildren().addAll(contents.getChildren().size() - 1, Arrays.asList(
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace\\dhjasdhjashdjashjdkhasjkdhjkak"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace\\dhjasdhjashdjashjdkhasjkdhjkak"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace\\dhjasdhjashdjashjdkhasjkdhjkak"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace\\dhjasdhjashdjashjdkhasjkdhjkak"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace\\dhjasdhjashdjashjdkhasjkdhjkak"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace\\dhjasdhjashdjashjdkhasjkdhjkak"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace\\dhjasdhjashdjashjdkhasjkdhjkak"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace\\dhjasdhjashdjashjdkhasjkdhjkak"),
				decorator.tile(new Pane(), image, "E:\\Workspace"),
				decorator.tile(new Pane(), image, "E:\\Workspace")));
	}
}
