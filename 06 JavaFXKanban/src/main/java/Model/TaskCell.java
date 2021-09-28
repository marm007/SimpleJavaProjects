package Model;

import java.io.IOException;

import Controllers.FormController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TaskCell extends ListCell<Task> {

    private ListView<Task> listView;

    public TaskCell(ListView<Task> listView) {
        this.listView = listView;
    }

    @Override
    public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (task == null || empty) {
            setText(null);
            setContextMenu(null);
            setStyle("");
        } else {

            hoverProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!isEmpty() && newValue) {
                        final Tooltip tooltip = new Tooltip();
                        tooltip.setText(task.getDescription());
                        setTooltip(tooltip);
                    }
                }
            });

            setText(getItem().getTitle());

            switch (getItem().getPriority()) {
                case LOW:
                    setStyle("-fx-control-inner-background: rgba(255,133,179,0.37) ;");
                    break;
                case MEDIUM:
                    setStyle("-fx-control-inner-background: rgba(255,132,49,0.56) ;");
                    break;
                case HIGH:
                    setStyle("-fx-control-inner-background: rgba(255,32,70,0.71) ;");
                    break;
            }

            ContextMenu contextMenu = new ContextMenu();

            MenuItem delete = new MenuItem("Delete");
            MenuItem edit = new MenuItem("Edit");

            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
                }
            });

            edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Parent root = null;
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI/formView.fxml"));
                        root = loader.load();

                        FormController formController = loader.getController();
                        formController.restoreTask(listView.getSelectionModel().getSelectedItem());

                        formController.setEditTask(true);

                        formController.setButtonText("Apllay changes");

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root, 550, 400));

                        stage.setMinWidth(550);
                        stage.setMinHeight(400);

                        stage.setTitle("Edit Model");
                        stage.show();

                        stage.setOnHiding(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                listView.getSelectionModel().clearSelection();
                                updateItem(task, false);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

            contextMenu.getItems().addAll(delete, edit);
            setContextMenu(contextMenu);

        }
    }
}
