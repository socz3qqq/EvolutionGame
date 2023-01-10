package evolutionGame.gui;

import evolutionGame.mapElements.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private final VBox vBox;
    private final Image image;
    private ImageView imageView;
    private Label animalPositionLabel;

    public GuiElementBox(IMapElement mapElement){
        {
            try {
                image = new Image(new FileInputStream(mapElement.getGraphicalRepresentation()));
                imageView = new ImageView(image);
                vBox = new VBox();
                imageView.setFitHeight(25);
                imageView.setFitWidth(25);
//                imageView.fitHeightProperty().bind(vBox.heightProperty());
//                imageView.fitWidthProperty().bind(vBox.widthProperty());
                vBox.getChildren().add(imageView);
                vBox.setAlignment(Pos.CENTER);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public VBox getElementToDisplay(){
        return this.vBox;
    }
}
