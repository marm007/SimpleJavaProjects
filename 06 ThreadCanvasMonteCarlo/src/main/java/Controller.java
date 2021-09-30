import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Canvas canvas;

    @FXML
    ProgressBar progressBar;

    @FXML
    Button startButton;

    @FXML
    Button stopButton;

    @FXML
    TextField numberOfPointsTextField;

    @FXML
    TextField resultTextField;

    private DrawerTask task;

    private boolean isCancelled = false;

    @FXML
    public void handleButtonAction() {

        if (numberOfPointsTextField.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please write some number of points!");
            alert.showAndWait();

            return;
        }

        try {

            int max = Integer.parseInt(numberOfPointsTextField.getText());

            if (!isCancelled) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                task = new DrawerTask(max, gc);
                progressBar.progressProperty().bind(task.progressProperty());
            } else {
                task = new DrawerTask(max, task.getGc(), task.getI(), task.getInArea(), task.getBi());
                progressBar.progressProperty().bind(task.progressProperty());
                isCancelled = false;
            }

            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    double integral = ((Math.abs(DrawerTask.MAX_X) + Math.abs(DrawerTask.MIN_X))
                            * (Math.abs(DrawerTask.MAX_X) + Math.abs(DrawerTask.MIN_X)))
                            * ((double) task.getInArea() / (double) task.getMax());
                    resultTextField.setText(String.valueOf(integral));
                }
            });

            new Thread(task).start();

        } catch (NumberFormatException e) {
            System.err.println(e);
        }

    }

    @FXML
    public void handleBtnStopAction() {
        task.cancel();
        isCancelled = true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
