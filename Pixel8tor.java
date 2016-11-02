
package pixel8tor;

import java.awt.Point;
import java.io.File;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Pixel8tor extends Application {

    public static int gridSize[] = new int[2];
    public static int pixSize[] = new int[2];

    public static GridPane gridStage = new GridPane();
    public static GridPane grid = new GridPane();
    private static ScrollPane map = new ScrollPane();
    private static StackPane root = new StackPane();
    private static Scene scene = new Scene(root, 1000, 800);
    private static VBox optBox = new VBox();
    public static String recentPaths[] = new String[20];

    public static String pixelSheet[][];

    @Override
    public void start(Stage primaryStage) {

        VBox colorBox = new VBox();
        colorBox.setPadding(new Insets(105, 105, 105, 105));

        ObservableList<String> names
                = FXCollections.observableArrayList();
        ListView ltTab = new ListView();
        ltTab.setMaxWidth(200);
        ltTab.setMinWidth(200);
        ltTab.setMaxHeight(300);
        ltTab.setMinHeight(300);
        ltTab.setStyle("-fx-font: 24px Rockwell");
        try {
            Scanner stdin = new Scanner(new File("recents.txt"));
            int i = 0;
            while (stdin.hasNext()) {
                Pixel8tor.recentPaths[i] = stdin.next();
                i++;
            }
            int c = 0;

            while (!recentPaths[c].isEmpty()) {
                names.add(recentPaths[c]);
                c++;
            }
        } catch (Exception e) {
        }

        ltTab.setItems(names);
        ltTab.setTranslateY(+250);
        ltTab.setTranslateX(-50);

        final ColorPicker colorPicker = new ColorPicker();
        Text txtColor = new Text("Color:       ");
        txtColor.setFont(Font.font("ROCKWELL", 20));
        txtColor.setFill(Color.WHITE);
        colorPicker.setValue(Color.CORAL);
        CheckBox eyeDropper = new CheckBox("Eyedropper");

        colorBox.getChildren().addAll(txtColor, colorPicker, eyeDropper);
        eyeDropper.setTranslateY(+20);
        eyeDropper.setTranslateX(-40);
        eyeDropper.setStyle("-fx-text-fill: white");
        colorBox.setAlignment(Pos.TOP_RIGHT);
        colorBox.setPickOnBounds(false);

        optBox.setMinHeight(scene.getHeight() - 40);
        optBox.setMaxHeight(scene.getHeight() - 40);
        optBox.setMaxWidth(300);
        optBox.setTranslateY(+20);
        optBox.setMouseTransparent(true);
        optBox.setAlignment(Pos.TOP_RIGHT);
        optBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.75);"
                + "-fx-padding: 00;"
                + "-fx-border-style: dashed;"
                + "-fx-border-width: 5;"
                + "-fx-border-insets: 15;"
                + "-fx-border-radius: 105;"
                + "-fx-border-color: white;");

        gridStage.setGridLinesVisible(true);
        gridStage.setStyle("-fx-background-color: rgba(255, 255, 255)");
        gridStage.setStyle("-fx-background-color: white;");

        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("_File");
        MenuItem newSheet = new MenuItem("_New");
        MenuItem saveSheet = new MenuItem("_Save");
        MenuItem loadSheet = new MenuItem("_Load");
        file.getItems().addAll(newSheet, saveSheet, loadSheet);
        menuBar.getMenus().add(file);

        Pane layers = new Pane();
        layers.getChildren().addAll(gridStage);//, grid);
        VBox mapBox = new VBox();
        primaryStage.setWidth(scene.getWidth());
        primaryStage.setHeight(scene.getHeight());
        map.setContent(layers);
        map.setMaxHeight(primaryStage.getHeight() - 30);
        map.setMaxWidth(primaryStage.getWidth() - 310);
        map.setMinHeight(primaryStage.getHeight() - 30);
        map.setMinWidth(primaryStage.getWidth() - 310);
        map.setLayoutX(0);

        grid.setGridLinesVisible(true);
        grid.setMouseTransparent(true);
        mapBox.getChildren().addAll(map);
        mapBox.setAlignment(Pos.TOP_LEFT);
        mapBox.setTranslateY(+25);
        mapBox.setPickOnBounds(false);

        root.setAlignment(Pos.TOP_RIGHT);
        root.setStyle("-fx-background-color: rgba(204, 204, 255)");
        root.getChildren().addAll(optBox, mapBox, colorBox, menuBar, ltTab);

        primaryStage.setTitle("PixMak");
        primaryStage.setScene(scene);
        primaryStage.show();

        ltTab.setCellFactory(param -> new ListCell<String>() {
            private Rectangle imageView = new Rectangle(20, 20, 20, 20);

            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(name);
                    try {
                        Image img = new Image((new File(name + ".png").toURI().toURL().toExternalForm()));
                        imageView.setFill(new ImagePattern(img));
                        setGraphic(imageView);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            }
        });

        ltTab.setOnMouseClicked((MouseEvent event) -> {
            Node node = gridStage.getChildren().get(0);
            if (gridSize[0] > 0) {
                askSave.box();
            }
            gridStage.getChildren().clear();
            gridStage.getChildren().add(0, node);
            configureGridLayout();
            Save.opnFile(ltTab.getSelectionModel().getSelectedItem().toString());
            configureGridLayout();

        });

        gridStage.setOnMouseDragged((MouseEvent event) -> {
            Point coord = new Point((int) (event.getX() / pixSize[0]),
                    (int) (event.getY() / pixSize[1]));

            Rectangle fill;
            if (event.isPrimaryButtonDown()) {

                fill = new Rectangle(pixSize[0], pixSize[1], colorPicker.getValue());
                try {
                    if (coord.x < gridSize[0]
                            && coord.y < gridSize[1]) {
                        pixelSheet[coord.y][coord.x] = colorPicker.getValue().toString();
                        gridStage.add(fill, coord.x, coord.y);
                    }

                } catch (Exception e) {
                }
            } else {
                pixelSheet[coord.y][coord.x] = ";)";
                fill = new Rectangle(pixSize[0], pixSize[1], Color.WHITE);
                fill.setStroke(Color.DIMGRAY);
                gridStage.add(fill, coord.x, coord.y);
            }

        });

        gridStage.setOnMouseClicked((MouseEvent event) -> {

            Point coord = new Point((int) (event.getX() / pixSize[0]),
                    (int) (event.getY() / pixSize[1]));

            Rectangle fill;

            if (event.getButton() == MouseButton.PRIMARY && eyeDropper.isSelected()) {
                try {
                    colorPicker.setValue(Color.web(pixelSheet[coord.y][coord.x]));
                    eyeDropper.setSelected(false);
                } catch (Exception e) {
                }
            } else if (event.getButton() == MouseButton.PRIMARY && !eyeDropper.isSelected()) {
                fill = new Rectangle(pixSize[0], pixSize[1], colorPicker.getValue());
                try {
                    if (coord.x < gridSize[0]
                            && coord.y < gridSize[1]) {
                        pixelSheet[coord.y][coord.x] = colorPicker.getValue().toString();
                        gridStage.add(fill, coord.x, coord.y);
                    }

                } catch (Exception e) {
                }
            } else {
                pixelSheet[coord.y][coord.x] = ";)";
                fill = new Rectangle(pixSize[0], pixSize[1], Color.WHITE);
                fill.setStroke(Color.DIMGRAY);
                gridStage.add(fill, coord.x, coord.y);

            }
        });

        newSheet.setOnAction((ActionEvent event) -> {

            NewFile.open();
            configureGridLayout();

        });

        saveSheet.setOnAction((ActionEvent event) -> {
            Save.saveFile(pixelSheet);

            configureGridLayout();

        });
        loadSheet.setOnAction((ActionEvent event) -> {
            if (gridSize[0] > 0) {
                askSave.box();
            }
            Save.openFile();
            Pixel8tor.configureGridLayout();

        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {

                configureGridLayout();
            }

        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneHeight) {
                configureGridLayout();
            }
        });

    }

    public static void configureGridLayout() {

        gridStage.getChildren().removeAll();
        gridStage.getRowConstraints().clear();
        gridStage.getColumnConstraints().clear();

        optBox.setMinHeight(scene.getHeight() - 20);
        optBox.setMaxHeight(scene.getHeight() - 20);

        gridStage.setGridLinesVisible(true);
        map.setMinHeight(scene.getHeight() - 25);
        map.setMaxHeight(scene.getHeight() - 25);
        map.setMaxWidth(scene.getWidth() - 300);
        map.setMinHeight(scene.getHeight() - 25);
        map.setMinWidth(scene.getWidth() - 300);

        for (int i = 0; i < gridSize[1]; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(pixSize[1]);
            rowConstraints.setPrefHeight(pixSize[1]);
            rowConstraints.setMaxHeight(pixSize[1]);
            rowConstraints.setValignment(VPos.CENTER);
            gridStage.getRowConstraints().add(rowConstraints);
            grid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < gridSize[0]; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(pixSize[0]);
            colConstraints.setMaxWidth(pixSize[0]);
            colConstraints.setPrefWidth(pixSize[0]);
            colConstraints.setHalignment(HPos.CENTER);
            gridStage.getColumnConstraints().add(colConstraints);
            grid.getColumnConstraints().add(colConstraints);
        }
        map.setVvalue(1.0);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
