package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileOperation {

    public static void save(Map<String, ListView> lists, Window root) {
        File recordsDir = new File(System.getProperty("user.home"), "kanban/records");
        if (!recordsDir.exists()) {
            recordsDir.mkdirs();
        }
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose directory to save");
        chooser.setInitialDirectory(recordsDir);
        File selectedDirectory = chooser.showDialog(root);

        if (selectedDirectory == null)
            return;

        for (Map.Entry<String, ListView> entry : lists.entrySet()) {

            try {
                List<Task> list = new ArrayList<Task>(entry.getValue().getItems());

                FileOutputStream fileOutputStream = new FileOutputStream(
                        selectedDirectory + "/" + entry.getKey() + ".txt");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(list);
                objectOutputStream.close();
                fileOutputStream.close();

                System.out.println("\nSerialization Successful... Checkout your specified output file..\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void open(Map<String, ListView> lists, Window root) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open saved lists");
        File recordsDir = new File(System.getProperty("user.home"), "kanban/records");
        fileChooser.setInitialDirectory(recordsDir);

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".txt", "*.txt"));

        List<File> files = fileChooser.showOpenMultipleDialog(root);

        if (files == null || files.size() == 0)
            return;

        for (File file : files) {

            String name = file.getName().substring(0, file.getName().length() - 4);

            if (lists.containsKey(name)) {

                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    List<Task> tasks = (ArrayList) objectInputStream.readObject();

                    lists.get(name).getItems().addAll(tasks);
                    lists.get(name).getSelectionModel().clearSelection();

                    objectInputStream.close();
                    fileInputStream.close();
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void serialize(Map<String, ListView> lists, Window root) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose directory to save");
        File recordsDir = new File(System.getProperty("user.home"), "kanban/records");
        if (!recordsDir.exists()) {
            recordsDir.mkdirs();
        }
        chooser.setInitialDirectory(recordsDir);
        File selectedDirectory = chooser.showDialog(root);

        if (selectedDirectory == null)
            return;

        for (Map.Entry<String, ListView> entry : lists.entrySet()) {
            JAXBContext ctx = null;
            try {
                ctx = JAXBContext.newInstance(Tasks.class);
                Marshaller marshaller = ctx.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                Tasks tasks = new Tasks();

                List<Task> tasksList = new ArrayList<>(entry.getValue().getItems());
                tasks.setTasks(tasksList);

                marshaller.marshal(tasks, new File(selectedDirectory + "/" + entry.getKey() + ".xml"));
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deserialize(Map<String, ListView> lists, Window root) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open saved lists");
        File recordsDir = new File(System.getProperty("user.home"), "kanban/records");
        fileChooser.setInitialDirectory(recordsDir);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".xml", "*.xml"));

        List<File> files = fileChooser.showOpenMultipleDialog(root);

        if (files == null || files.size() == 0)
            return;

        for (File file : files) {

            String name = file.getName().substring(0, file.getName().length() - 4);

            if (lists.containsKey(name)) {

                JAXBContext ctx = null;
                try {
                    ctx = JAXBContext.newInstance(Tasks.class);
                    Unmarshaller unmarshaller = ctx.createUnmarshaller();

                    Tasks tasks = (Tasks) unmarshaller.unmarshal(new File(String.valueOf(file)));

                    lists.get(name).getItems().addAll(tasks.getTasks());
                    lists.get(name).getSelectionModel().clearSelection();

                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
