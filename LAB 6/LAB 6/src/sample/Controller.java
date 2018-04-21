package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class Controller implements PointListener {

    private DrawerTask task;
    @FXML
    private Canvas canvas;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TextField textFieldScore;
    @FXML
    private TextField textFieldNrPoints;
    @FXML
    private BufferedImage bi;
    @FXML
    private GraphicsContext gc;

    @FXML
    private void handleRunBtnAction(ActionEvent event){

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUEVIOLET);
        gc.fillRect(gc.getCanvas().getLayoutX(),gc.getCanvas().getLayoutY(),gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        bi= new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        if(Integer.parseInt(textFieldNrPoints.getText())>0) {
            task = new DrawerTask(Integer.parseInt(textFieldNrPoints.getText()));
            task.setListener(this);
            progressBar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
           // double result =(double) task.getValue();
            // textFieldScore.setText(Double.toString(result));
           // progressBar.progressProperty().bind(task.progressProperty());
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Cannot make calculations!");
            alert.setContentText("You gave the wrong value!");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleStopBtnAction(ActionEvent event){
        task.cancel();
    }

   // @FXML
    public void pointFound(PointEvent event) {
        int pointX=(int)((gc.getCanvas().getWidth())*(event.getPointX()+8)/16);
        int pointY=(int)((gc.getCanvas().getHeight())*(event.getPointY()+8)/16);
        pointY=400-pointY;

        if(event.isOnGraph()){
            bi.setRGB(pointX,pointY,java.awt.Color.YELLOW.getRGB());
        }
        else{
             bi.setRGB(pointX,pointY,java.awt.Color.BLUE.getRGB());
        }
        if(event.getCounter()%100==0){
        gc.drawImage(SwingFXUtils.toFXImage(bi,null),0,0);
        }
    }
//    @FXML
//    private void textBoxNrPointEnter(KeyEvent event){
//
//        if (event.KEY_PRESSED.getName() == "0" && event.KEY_PRESSED.getName() == "9")// Sprawdzamy czy wciśnięty jest liczbą albo klawiszem backspace
//        {
//            textFieldNrPoints.setText(textFieldNrPoints.getText()+KeyEvent.KEY_PRESSED.getName());
//        }
//    }
}
