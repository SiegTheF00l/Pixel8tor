package pixel8tor;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static pixel8tor.Pixel8tor.gridStage;

public class NewFile {

    public static void open() {
        VBox infoBox = new VBox();
        infoBox.setSpacing(10);

        Label lblGridSize = new Label("Grid Size: ");
        HBox gsBox = new HBox();
        TextField xgDim = new TextField();
        xgDim.setMaxWidth(75);
        Label lblX = new Label(" X ");
        Label ygDim = new Label();
        ygDim.setMaxWidth(75);
        gsBox.getChildren().addAll(xgDim, lblX, ygDim);

        Label lblPixSize = new Label("Pixel Size: ");
        HBox psBox = new HBox();
        TextField xpDim = new TextField();
        xpDim.setMaxWidth(75);
        Label ypDim = new Label();
        ypDim.setMaxWidth(75);
        psBox.getChildren().addAll(xpDim, new Label(" X "), ypDim);

        HBox btnBox = new HBox();
        Button btnOk = new Button("_OK");
        Button btnCancel = new Button("_Cancel");
        btnBox.getChildren().addAll(btnOk, btnCancel);
        btnBox.setSpacing(30);
        btnBox.setAlignment(Pos.BOTTOM_CENTER);

        infoBox.getChildren().addAll(lblGridSize, gsBox, lblPixSize, psBox, btnBox);
        Stage stage = new Stage();
        stage.setTitle("New File");
        stage.setScene(new Scene(infoBox, 170, 150));
        stage.show();

        xgDim.textProperty().addListener((obs, oldText, newText) -> {
            ygDim.setText(xgDim.getText());
        });
        xpDim.textProperty().addListener((obs, oldText, newText) -> {
            ypDim.setText(xpDim.getText());
        });
        btnOk.setOnMouseClicked((MouseEvent event) -> {
            Pixel8tor.gridSize[0] = Integer.parseInt(xgDim.getText());
            Pixel8tor.gridSize[1] = Integer.parseInt(ygDim.getText());
            Pixel8tor.pixSize[0] = Integer.parseInt(xpDim.getText());
            Pixel8tor.pixSize[1] = Integer.parseInt(ypDim.getText());
            Pixel8tor.pixelSheet = new String[Pixel8tor.gridSize[0]][Pixel8tor.gridSize[1]];
            Node node = gridStage.getChildren().get(0);
            gridStage.getChildren().clear();
            gridStage.getChildren().add(0, node);
            Pixel8tor.configureGridLayout();

            stage.close();
        });

        btnCancel.setOnMouseClicked((MouseEvent event) -> {
            stage.close();
        });

    }
}
