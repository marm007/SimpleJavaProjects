package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Model.Task;
import Model.TaskPriority;

public class FormController implements Initializable {

    private static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    @FXML
    Button addButton;

    @FXML
    TextField title;

    @FXML
    ComboBox<TaskPriority> priority;

    @FXML
    DatePicker expireDate;

    @FXML
    TextArea taskDescription;

    private ListView listView;

    private boolean editTaskMode = false;

    private Task editingTask;

    public void addButtonClick() {
        if (editTaskMode) {
            if (validateData()) {

                editingTask.setTitle(title.getText());
                editingTask.setPriority(priority.getSelectionModel().getSelectedItem());
                editingTask.setExpireDate(expireDate.getValue());
                editingTask.setDescription(taskDescription.getText());

                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.close();
            }
        } else {
            if (validateData()) {
                listView.getItems().add(new Task(title.getText(), priority.getSelectionModel().getSelectedItem(),
                        expireDate.getValue(), taskDescription.getText()));
                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<TaskPriority> items = FXCollections.observableArrayList(TaskPriority.LOW, TaskPriority.MEDIUM,
                TaskPriority.HIGH);
        priority.setItems(items);
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    private boolean isDateValid() {
        if (expireDate.getEditor().getText().isEmpty())
            return false;
        else {
            try {
                LocalDate.from(DATE_FORMATTER.parse(expireDate.getEditor().getText()));
                return true;
            } catch (DateTimeException parseExc) {
                return false;
            }
        }
    }

    private boolean validateData() {
        if (title.getText().isEmpty() || priority.getSelectionModel().getSelectedItem() == null || !isDateValid()
                || taskDescription.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Fields cannot be empty!");

            if (title.getText().isEmpty())
                alert.setContentText(alert.getContentText() + "Fill title field!\n\n");
            if (priority.getSelectionModel().getSelectedItem() == null)
                alert.setContentText(alert.getContentText() + "Fill priority field!\n\n");
            if (expireDate.getEditor().getText().isEmpty())
                alert.setContentText(alert.getContentText() + "Fill expire date field!\n\n");
            else if (!isDateValid())
                alert.setContentText(alert.getContentText() + "Expire date format expected " + DATE_PATTERN + " !\n\n");
            if (taskDescription.getText().isEmpty())
                alert.setContentText(alert.getContentText() + "Fill description field!\n\n");

            alert.show();

            return false;

        }

        return true;
    }

    public void restoreTask(Task selectedItem) {
        this.editingTask = selectedItem;
        this.title.setText(selectedItem.getTitle());
        this.taskDescription.setText(selectedItem.getDescription());
        this.expireDate.setValue(selectedItem.getExpireDate());
        this.priority.getSelectionModel().select(selectedItem.getPriority());
    }

    public void setEditTask(boolean editTaskMode) {
        this.editTaskMode = editTaskMode;
    }

    public void setButtonText(String apllay_changes) {
        addButton.setText(apllay_changes);
    }
}
