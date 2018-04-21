package sample;

import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class Controller //implements PointListener {
{

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
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int amount=Integer.parseInt(textFieldNrPoints.getText());
        if(amount>100){
            task = new DrawerTask(gc,Integer.parseInt(textFieldNrPoints.getText()));
            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    double result =(double) task.getValue();
                    textFieldScore.setText(Double.toString(result));
                }
            });
            progressBar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
            float result =(float) task.getValue();
            textFieldScore.setText(Double.toString(result));
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

   // @Override
   // public void pointFound(PointEvent event) {
//        int pointX=(int)((gc.getCanvas().getWidth())*(event.pointX+8)/16);
//        int pointY=(int)((gc.getCanvas().getHeight())*(event.getPointY()+8)/16);
//        pointY=400-pointY;
//
//        if(event.isOnGraph()){
//            bi.setRGB((int)pointX, (int)pointY,java.awt.Color.YELLOW.getRGB());
//        }
//        else{
//             bi.setRGB((int)pointX, (int)pointY,java.awt.Color.BLUE.getRGB());
//        }
//         if(event.getCounter()%1000==0){
//        gc.drawImage(SwingFXUtils.toFXImage(bi,null),0,0);
        //}
  //  }
}
