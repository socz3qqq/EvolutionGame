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
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                animalPositionLabel = new Label(mapElement.getPosition().toString());

                vBox = new VBox(imageView, animalPositionLabel);
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
