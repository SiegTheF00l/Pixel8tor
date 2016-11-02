
package pixel8tor;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;
import javafx.embed.swing.SwingFXUtils;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import static pixel8tor.Pixel8tor.gridStage;
import static pixel8tor.Pixel8tor.pixSize;

/**
 *
 * @author cmason
 */
public class Save {
    
    public static String name = "L3DGARD";
    public static boolean showGridLines = true;
    
    
     private static void saveRecentPaths(File saveFile) {
         try {
        PrintStream P = new PrintStream(new File("recents.txt"));
        
        int i = 0;
        P.println(saveFile);
        while (!Pixel8tor.recentPaths[i].isEmpty()) {
            P.println(Pixel8tor.recentPaths[i]);
            i++;
        }
        P.close();
         }catch(Exception e){}
    }
     
     public static void saveFile(String array[][]) {
        PrintStream P;
        String fileName;
        Rectangle fill;
      
       Node node = gridStage.getChildren().get(0);
       gridStage.getChildren().clear();
       gridStage.getChildren().add(0,node);
        for (int i= 0; i < Pixel8tor.gridSize[0]; i++) {
                for (int j = 0 ; j < Pixel8tor.gridSize[1]; j++) {
                    try {
                    if (!Pixel8tor.pixelSheet[j][i].equals(";)")) {
                        try {
                        fill = new Rectangle(pixSize[0], pixSize[1], Color.web(Pixel8tor.pixelSheet[j][i]));
                        fill.setStroke(Color.web(Pixel8tor.pixelSheet[j][i]));
                        gridStage.add(fill, i, j);
                        
                        } catch(Exception e) {}
                    } }catch(Exception e){} 
                }
            }

        try {
            
            if ("L3DGARD".equals(name)) {
                fileName = saveName();
                P = new PrintStream(fileName +".txt");
            }
            else {
                fileName = name;
                P = new PrintStream(name);
            }
            P.println(Pixel8tor.gridSize[0] + "   " + Pixel8tor.gridSize[1]);
            P.println(Pixel8tor.pixSize[0] + "   " + Pixel8tor.pixSize[1]);
            for (int i = 0; i < Pixel8tor.gridSize[1]; i++) {
                for (int count = 0; count < Pixel8tor.gridSize[0]; count++){
                    if (array[count][i] == null)
                       P.print(";) ");
                    else
                        P.print(array[count][i] + " ");
                }
                P.println();
            }
            P.close();
       saveRecentPaths(new File(fileName));
       gridStage.setStyle("-fx-background-opacity: 0; -fx-grid-lines-visible: false");
       SnapshotParameters parameters = new SnapshotParameters();
       parameters.setFill(javafx.scene.paint.Color.TRANSPARENT);
       WritableImage image = gridStage.snapshot(parameters, null);

    File file = new File(fileName+".png");

    try {
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

              
    } catch (IOException e) {

    }

        } catch (Exception e) {
        }

        gridStage.setGridLinesVisible(true);
        gridStage.setStyle("-fx-background-opacity: 1;");
        gridStage.setStyle("-fx-background-color: white;");
    }
    
     public static void msgBox(String dialogue) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("");
        alert.setContentText(dialogue); 
        
        alert.showAndWait();
    }
    
        public static String inpBox(String dialogue) {
        String input;
        
        input = "";
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("");
        dialog.setContentText(dialogue);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
        input = result.get();
        
        }
        return input;
}
        
           public static String saveName() {
        String name;
        int numLessThan;
        
        name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                while (name.length() > 8) {
                    name = inpBox("File Name: ");
                    try {
                        if (name.length() > 8) {
                            msgBox("Name too long, Maximum size 8 "
                                    + "characters");
                        }
                    } catch (Exception e) {
                        name = "        ";
                    }
                }
                
                return name;
    }
    
    
        private static File getOpenPath() {
        File path;

        final JFileChooser fc = new JFileChooser();
        int returnVal;
        
        returnVal = fc.showOpenDialog(null);

        path = null;
        if (returnVal == 0) {
            path = fc.getSelectedFile();
        }

        
        return path;
    }
        
        public static void openFile() {
            try {
             File open = getOpenPath();
             System.out.println(open);
            Scanner inFile = new Scanner(open);
            
            System.out.println(open);
            Pixel8tor.gridSize[0] = Integer.parseInt(inFile.next());
            System.out.println(Pixel8tor.gridSize[0]);
            Pixel8tor.gridSize[1] = Integer.parseInt(inFile.next());
            System.out.println(Pixel8tor.gridSize[1]);
            Pixel8tor.pixSize[0] = Integer.parseInt(inFile.next());
            Pixel8tor.pixSize[1] = Integer.parseInt(inFile.next());
            
           Pixel8tor.pixelSheet = new String[Pixel8tor.gridSize[0]][Pixel8tor.gridSize[1]];
            Rectangle fill;
       
            for (int j = 0; j < Pixel8tor.gridSize[0]; j++) {
                for (int i = 0 ; i < Pixel8tor.gridSize[1]; i++) {
                    Pixel8tor.pixelSheet[i][j] = inFile.next();
                    
                    if (!Pixel8tor.pixelSheet[i][j].equals(";)")) {
                        fill = new Rectangle(pixSize[0], pixSize[1], Color.web(Pixel8tor.pixelSheet[i][j]));
                        gridStage.add(fill, j, i);
                    }
                   
                }
            }
           Pixel8tor.configureGridLayout();
          
           
           
            }catch(Exception e){
            System.out.println("Error");}
        }
        
        public static void opnFile(String name) {
            try {

           File load = new File(new File(name + ".txt").getAbsolutePath());
            Scanner inFile = new Scanner(load);
          
            Pixel8tor.gridSize[0] = Integer.parseInt(inFile.next());
            System.out.println(Pixel8tor.gridSize[0]);
            Pixel8tor.gridSize[1] = Integer.parseInt(inFile.next());
            System.out.println(Pixel8tor.gridSize[1]);
            Pixel8tor.pixSize[0] = Integer.parseInt(inFile.next());
            Pixel8tor.pixSize[1] = Integer.parseInt(inFile.next());
            
           Pixel8tor.pixelSheet = new String[Pixel8tor.gridSize[0]][Pixel8tor.gridSize[1]];
            Rectangle fill;

            for (int i= 0; i < Pixel8tor.gridSize[0]; i++) {
                for (int j = 0 ; j < Pixel8tor.gridSize[1]; j++) {
                    Pixel8tor.pixelSheet[j][i] = inFile.next();
                    
                    if (!Pixel8tor.pixelSheet[j][i].equals(";)")) {
                        try {
                        fill = new Rectangle(pixSize[0], pixSize[1], Color.web(Pixel8tor.pixelSheet[j][i]));
                        fill.setStroke(Color.web(Pixel8tor.pixelSheet[j][i]));
                        gridStage.add(fill, i, j);
                        
                        } catch(Exception e) {
                          fill = new Rectangle(pixSize[0], pixSize[1], Color.TRANSPARENT); 
                          fill.setStroke(Color.BLACK);
                          gridStage.add(fill, i, j);
                        }
                        gridStage.setGridLinesVisible(true);
                    } 
                }
            }
           Pixel8tor.configureGridLayout();

            }catch(Exception e){
            System.out.println(e);}
        }
        
}
