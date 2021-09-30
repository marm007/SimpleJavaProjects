package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import Model.FileOperation;
import Model.Task;
import Model.TaskCellFactory;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController implements Initializable {

    private static final DataFormat TAB_TYPE = new DataFormat("nonserializableObject/task");
    private final ObjectProperty<ListCell<Task>> dragSource = new SimpleObjectProperty<>();

    @FXML
    ListView todoListView;

    @FXML
    ListView inProgressListView;

    @FXML
    ListView doneListView;

    @FXML
    MenuBar menuBar;

    @FXML
    Menu aboutMenu;

    @FXML
    Button addNewTaskButton;

    private Dragboard dragboard;
    private Map<String, ListView> lists;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lists = new HashMap<>();
        lists.put("todo", todoListView);
        lists.put("in_progress", inProgressListView);
        lists.put("done", doneListView);

        Label menuLabel = new Label("About");
        menuLabel.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About author");
            alert.setHeaderText("");
            alert.setContentText("Marcin Mieszczak\n IS AGH WIMIIP");
            alert.showAndWait();
        });
        aboutMenu.setGraphic(menuLabel);

        todoListView.setCellFactory(new TaskCellFactory());
        inProgressListView.setCellFactory(new TaskCellFactory());
        doneListView.setCellFactory(new TaskCellFactory());
    }

    public void onDragDetected(MouseEvent e) {
        ListView listView = null;

        if (e.getSource() == todoListView)
            listView = todoListView;
        else if (e.getSource() == inProgressListView)
            listView = inProgressListView;
        else if (e.getSource() == doneListView)
            listView = doneListView;

        if (listView != null && listView.getSelectionModel().getSelectedItem() != null) {
            dragboard = listView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.put(TAB_TYPE, listView.getSelectionModel().getSelectedItem());
            dragboard.setContent(content);
            listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            listView.getSelectionModel().clearSelection();
        }
    }

    public void onDragOver(DragEvent e) {
        e.acceptTransferModes(TransferMode.MOVE);
    }

    public void onDragDropped(DragEvent e) {
        if (e.getGestureTarget() == todoListView)
            todoListView.getItems().add(dragboard.getContent(TAB_TYPE));
        else if (e.getGestureTarget() == inProgressListView)
            inProgressListView.getItems().add(dragboard.getContent(TAB_TYPE));
        else if (e.getGestureTarget() == doneListView)
            doneListView.getItems().add(dragboard.getContent(TAB_TYPE));
    }

    public void closeApp() {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    public void showAddNewTaskForm() {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI/formView.fxml"));
            root = loader.load();

            FormController formController = loader.getController();
            formController.setListView(todoListView);

            Stage stage = new Stage();
            stage.setTitle("Add new task");

            stage.setMinWidth(550);
            stage.setMinHeight(400);

            stage.setScene(new Scene(root, 550, 400));
            stage.show();

            menuBar.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    stage.close();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void save() {
        FileOperation.save(lists, menuBar.getScene().getWindow());
    }

    public void open() {
        FileOperation.open(lists, menuBar.getScene().getWindow());
    }

    public void serialize() {
        FileOperation.serialize(lists, menuBar.getScene().getWindow());
    }

    public void deserialize() {
        FileOperation.deserialize(lists, menuBar.getScene().getWindow());

    }

}
