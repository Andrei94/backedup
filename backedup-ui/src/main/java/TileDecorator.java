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

	Pane tile(Pane pane, Image image, String text) {
		pane.setPrefHeight(prefHeight);
		pane.setPrefWidth(prefWidth);
		pane.setCache(true);
		pane.setCacheHint(CacheHint.SPEED);
		pane.getStyleClass().add("pane");
		pane.getChildren().add(center(new VBox(new ImageView(image), centeredWrapableText(new Label(text)))));
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
}
