import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

class TileDecorator {
	private int prefHeight = 150;
	private int prefWidth = 150;

	Pane tile(Image image, Folder folder) {
		return tile(image, folder.path.toString());
	}

	private Pane tile(Image image, String text) {
		Pane pane = new Pane();
		pane.setPrefHeight(prefHeight);
		pane.setPrefWidth(prefWidth);
		pane.setCache(true);
		pane.setCacheHint(CacheHint.SPEED);
		pane.getStyleClass().add("pane");
		pane.getChildren().add(center(new VBox(new ImageView(image), centeredWrapableText(new Label(text)))));
		pane.getChildren().add(progressImage());
		return pane;
	}

	private VBox center(VBox node) {
		node.setFillWidth(true);
		node.setPrefHeight(prefHeight);
		node.setPrefWidth(prefWidth);
		node.setAlignment(Pos.CENTER);
		return node;
	}

	private Label centeredWrapableText(Label label) {
		label.setWrapText(true);
		label.setAlignment(Pos.CENTER);
		label.setLabelFor(label.getParent());
		return label;
	}

	private ImageView progressImage() {
		ImageView image = new ImageView();
		image.setPreserveRatio(true);
		image.setPickOnBounds(true);
		image.setFitHeight(40);
		image.setFitWidth(40);
		return image;
	}
}
