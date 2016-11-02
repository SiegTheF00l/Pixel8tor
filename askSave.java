package pixel8tor;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class askSave {
    
    public static void box(){
        VBox infoBox = new VBox();
                infoBox.setSpacing(10);
                
                Label lblText = new Label("Do you want to save?");
                
                HBox btnBox = new HBox();
                Button btnYes = new Button("_Yes");
                Button btnNo = new Button("_No");
                btnBox.getChildren().addAll(btnYes, btnNo);
                btnBox.setSpacing(30);
                btnBox.setAlignment(Pos.BOTTOM_CENTER);
                
                infoBox.getChildren().addAll(lblText, btnBox);
                Stage stage = new Stage();
                stage.setTitle("Save?");
                stage.setScene(new Scene(infoBox, 170, 150));
                stage.show();
                
                btnYes.setOnMouseClicked((MouseEvent event) -> {
                    Save.saveFile(Pixel8tor.pixelSheet);
                    stage.close();
                });
                
                btnNo.setOnMouseClicked((MouseEvent event) -> {
                     stage.close();
            });
            
    }
}
