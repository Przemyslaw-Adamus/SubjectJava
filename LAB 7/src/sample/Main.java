package sample;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application implements Serializable {

    private MenuItem exitItem;
    private MenuItem saveItem;
    private MenuItem loadItem;
    private MenuItem loadAsItem;
    private MenuItem saveAsItem;
    private MenuItem loadAllItem;
    private MenuItem saveAllItem;
    private Menu menuFile;
    private Menu menuAbout;
    private MenuBar menuBar;
    private ListView<Task> toDoList;
    private ObservableList<Task> toDoItems;
    private ListView<Task> inProgressList;
    private ObservableList<Task> inProgressItems;
    private ListView<Task> doneList;
    private ObservableList<Task> doneItems;
    private Button button;
    private Button buttonSaveAs;
    private Button buttonLoadAs;
    private Button buttonSave;
    private Button buttonLoad;
    private ComboBox comboBox;
    private BorderPane layoutPane;
    private Scene scene;
    private Task editTask;
    private ComboBox comboBoxIO;
    private FileChooser fileChooser;
    private Stage  fileChooserStage;
    private File selectedFile;
    private Alert alert;
    private FileChooser.ExtensionFilter serializeFilter;
    private FileChooser.ExtensionFilter csvFilter;
    private FileChooser.ExtensionFilter jsonFilter;

    @Override
    public void start(Stage primaryStage) {
        layoutPane = new BorderPane();
        scene = new Scene(layoutPane, 500,620);
        primaryStage.setResizable(false);
        fileChooserStage = new Stage();

        menuInit();
        listInit();
        buttonInit();
        contextMenuInit();
        dragAndDropInit();
        tooltipInit();
        primaryStage.setTitle("Kanban");
        primaryStage.setScene(scene);
        primaryStage.show();
        fileChooserInit();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void contextMenuInit() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(toDoList.isFocused())
                    toDoItems.remove(toDoList.getSelectionModel().getSelectedItem());
                else if(inProgressList.isFocused())
                    inProgressItems.remove(inProgressList.getSelectionModel().getSelectedItem());
                else if(doneList.isFocused())
                    doneItems.remove(doneList.getSelectionModel().getSelectedItem());
            }
        });
        MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO
                if(toDoList.isFocused())
                    editTask = toDoList.getSelectionModel().getSelectedItem();
                else if(inProgressList.isFocused())
                    editTask = inProgressList.getSelectionModel().getSelectedItem();
                else if(doneList.isFocused())
                    editTask = doneList.getSelectionModel().getSelectedItem();
                editInit();
            }
        });
        contextMenu.getItems().addAll(edit,delete);
        toDoList.setContextMenu(contextMenu);
        toDoList.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(toDoList,event.getScreenX(),event.getScreenY());
            }
        });
        inProgressList.setContextMenu(contextMenu);
        inProgressList.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(inProgressList,event.getScreenX(),event.getScreenY());
            }
        });
        doneList.setContextMenu(contextMenu);
        doneList.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(doneList,event.getScreenX(),event.getScreenY());
            }
        });
    }

    public void menuInit() {
        menuBar = new MenuBar();
        menuFile = new Menu("File");
        exitItem = new MenuItem("Exit");
        saveItem = new MenuItem("Save");
        loadItem = new MenuItem("Load");
        loadAsItem = new MenuItem("LoadAs");
        saveAsItem = new MenuItem("SaveAs");
        saveAllItem = new MenuItem("SaveAll");
        loadAllItem = new MenuItem("LoadAll");
        menuAbout = new Menu("");
        menuFile.getItems().add(exitItem);
        menuFile.getItems().add(saveItem);
        menuFile.getItems().add(loadItem);
        menuFile.getItems().add(saveAsItem);
        menuFile.getItems().add(loadAsItem);
        menuFile.getItems().add(saveAllItem);
        menuFile.getItems().add(loadAllItem);
        menuEventHandler();
        menuBar.getMenus().addAll(menuFile,menuAbout);
        layoutPane.setTop(menuBar);
    }

    public void tooltipInit() {
        Callback callback = new Callback<ListView<Task>, ListCell<Task>>() {
            public ListCell<Task> call(ListView<Task> param) {
                final Label label = new Label();
                final Tooltip tooltip = new Tooltip();
                final ListCell<Task> cell = new ListCell<Task>()
                {
                    @Override
                    public void updateItem(Task item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null)
                        {
                            Tooltip tooltip = new Tooltip();
                            tooltip.setText("Title: " + item.title + "\n" +
                                            "Priority: " + item.priorityStatus + "\n" +
                                            "Deadline: " + item.deadlineDate.toString() + "\n" +
                                            "Description: " + item.description + "\n" );
                            setTooltip(tooltip);
                            setText(item.title);
                            if(item.getPriorityStatus().equals("Low Priority"))
                            {
                                setStyle("-fx-background-color: #00FF00; -fx-text-fill: black");
                            }
                            else if(item.getPriorityStatus().equals("Medium Priority"))
                            {
                                setStyle("-fx-background-color: #FFFF00; -fx-text-fill: black");
                            }
                            else if(item.getPriorityStatus().equals("High Priority"))
                            {
                                setStyle("-fx-background-color: #FF0000; -fx-text-fill: black");
                            }
                        }
                        else
                        {
                            setText(null);
                            setStyle(null);
                        }
                    }
                };
                return cell;
            }
        };
        toDoList.setCellFactory(callback);
        doneList.setCellFactory(callback);
        inProgressList.setCellFactory(callback);

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            }
        };
        toDoList.getSelectionModel().selectedItemProperty().addListener(changeListener);
        doneList.getSelectionModel().selectedItemProperty().addListener(changeListener);
        inProgressList.getSelectionModel().selectedItemProperty().addListener(changeListener);
        
        
    }

    public void dragAndDropInit() {
        DataFormat taskDataFormat = new DataFormat("task");
        EventHandler<DragEvent> dragOver = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if(event.getDragboard().hasContent(taskDataFormat));
                event.acceptTransferModes(TransferMode.MOVE);
                event.consume();
            }
        };
        inProgressList.setOnDragOver(dragOver);
        toDoList.setOnDragOver(dragOver);
        doneList.setOnDragOver(dragOver);

        EventHandler<MouseEvent> dragDetected = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard dragBoard = null;
                ClipboardContent content = new ClipboardContent();
                if(inProgressList.isFocused()) {
                    dragBoard = inProgressList.startDragAndDrop(TransferMode.MOVE);
                    content.put(taskDataFormat, new ArrayList<Task>(inProgressList.getSelectionModel().getSelectedItems()));
                    inProgressItems.remove(inProgressList.getSelectionModel().getSelectedItem());
                }
                else if(toDoList.isFocused())
                {
                    dragBoard = toDoList.startDragAndDrop(TransferMode.MOVE);
                    content.put(taskDataFormat, new ArrayList<Task>(toDoList.getSelectionModel().getSelectedItems()));
                    toDoItems.remove(toDoList.getSelectionModel().getSelectedItem());
                }
                else if(doneList.isFocused())
                {
                    dragBoard = doneList.startDragAndDrop(TransferMode.MOVE);
                    content.put(taskDataFormat, new ArrayList<Task>(doneList.getSelectionModel().getSelectedItems()));
                    doneItems.remove(doneList.getSelectionModel().getSelectedItem());
                }
                dragBoard.setContent(content);
            }
        };
        inProgressList.setOnDragDetected(dragDetected);
        toDoList.setOnDragDetected(dragDetected);
        doneList.setOnDragDetected(dragDetected);

        inProgressList.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                List<Task> tasks = (List<Task>) event.getDragboard().getContent(taskDataFormat);
                inProgressItems.addAll(tasks);
                event.acceptTransferModes(TransferMode.MOVE);
                event.setDropCompleted(true);
                event.consume();
            }
        });
        /* TASKS TO DO LIST */
        toDoList.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                List<Task> tasks = (List<Task>) dragEvent.getDragboard().getContent(taskDataFormat);
                toDoItems.addAll(tasks);
                dragEvent.acceptTransferModes(TransferMode.MOVE);
                dragEvent.setDropCompleted(true);
                dragEvent.consume();
            }
        });
        /* DONE LIST */
        doneList.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                List<Task> tasks = (List<Task>) event.getDragboard().getContent(taskDataFormat);
                doneItems.addAll(tasks);
                event.acceptTransferModes(TransferMode.MOVE);
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    public void editInit() {
        boolean editing = true;
        buttonPressed(false,editing);
    }

    public void menuEventHandler() {
        exitItem.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        Label menuLabel = new Label("Author");
        menuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(" Author ");
                alert.setHeaderText(null);
                alert.setContentText("AUTHOR: Przemysław Adamus");
                alert.showAndWait();
            }
        });
        menuAbout.setGraphic(menuLabel);
        saveAsItem.setOnAction((ActionEvent t) -> {
            buttonSavePressed(true, true);
        });
        loadAsItem.setOnAction((ActionEvent t) -> {
            buttonLoadPressed(true, true);
        });
        saveItem.setOnAction((ActionEvent t) -> {
            save();
        });
        loadItem.setOnAction((ActionEvent t) -> {
            load();
        });
        saveAllItem.setOnAction((ActionEvent t) -> {
            comboBoxIO.setValue("ALL");
            save();
        });
        loadAllItem.setOnAction((ActionEvent t) -> {
            comboBoxIO.setValue("ALL");
            load();
        });
        //TU!!!!!!!!!!!

    }

    public void listInit() {
        /* To-Do list init */
        toDoList = new ListView<>();
        toDoItems = FXCollections.observableArrayList();
        toDoList.setItems(toDoItems);

        toDoList.setPrefSize(155,375); //150 / 100
        toDoList.setEditable(true);
        layoutPane.setLeft(toDoList);
        Label toDoLabel = new Label("TO DO");
        VBox vboxToDo = new VBox();
        vboxToDo.getChildren().addAll(toDoLabel,toDoList);
        vboxToDo.setAlignment(Pos.TOP_CENTER);
        layoutPane.setLeft(vboxToDo);



        inProgressList = new ListView<>();
        inProgressItems = FXCollections.observableArrayList();

        inProgressList.setItems(inProgressItems);
        inProgressList.setPrefSize(155,375);
        inProgressList.setEditable(true);
        Label inProgressLabel = new Label("IN PROGRES");
        VBox vboxInProgress = new VBox();
        vboxInProgress.getChildren().addAll(inProgressLabel,inProgressList);
        vboxInProgress.setAlignment(Pos.TOP_CENTER);
        layoutPane.setCenter(vboxInProgress);

        doneList = new ListView<>();
        doneItems = FXCollections.observableArrayList();

        doneList.setItems(doneItems);
        doneList.setPrefSize(155,375);
        doneList.setEditable(true);
        Label doneLabel = new Label("DONE");
        VBox vboxDone = new VBox();
        vboxDone.getChildren().addAll(doneLabel,doneList);
        vboxDone.setAlignment(Pos.TOP_CENTER);
        layoutPane.setRight(vboxDone);
    }

    public void buttonInit() {
        button = new Button("ADD NEW TASK");
        button.setPrefSize(180,30);
        button.setCursor(Cursor.CLOSED_HAND);
        button.setFont(Font.font(18));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean adding = true;
                buttonPressed(adding,false);
            }
        });

        buttonLoadAs=new Button("LoadAs");
        buttonLoadAs.setPrefSize(180,30);
        buttonLoadAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean adding = true;
                buttonLoadPressed(adding,false);
            }
        });

        buttonSaveAs=new Button("SaveAs");
        buttonSaveAs.setPrefSize(180,30);
        buttonSaveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean adding = true;
                buttonSavePressed(adding,false);
            }
        });

        buttonSave=new Button("Save(Export)");
        buttonSave.setPrefSize(180,30);
        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean adding = true;
                buttonExportPressed(adding,false);
            }
        });

        buttonLoad=new Button("Load(Import)");
        buttonLoad.setPrefSize(180,30);
        buttonLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean adding = true;
                buttonImportPressed(adding,false);
            }
        });

        comboBoxIOInit();
        VBox vboxToDo = new VBox();
        vboxToDo.getChildren().addAll(comboBoxIO,buttonSaveAs,buttonSave,buttonLoadAs,buttonLoad,button);
        vboxToDo.setAlignment(Pos.TOP_CENTER);
        layoutPane.setBottom(vboxToDo);
        BorderPane.setMargin(vboxToDo,new Insets(10,10,10,10));
    }

    private void buttonImportPressed(boolean adding, boolean b) {
        load();
    }

    private void buttonExportPressed(boolean adding, boolean b) {
        save();
    }

    private void buttonSavePressed(boolean adding, boolean b) {
        fileChooser.setTitle("Open Resource File");
        selectedFile=fileChooser.showOpenDialog(fileChooserStage);
        save();
    }

    private void buttonLoadPressed(boolean adding, boolean b) {
        fileChooser.setTitle("Open Resource File");
        selectedFile=fileChooser.showOpenDialog(fileChooserStage);
        load();
    }

    public void buttonPressed(boolean adding, boolean editing) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        Scene sceneButton = new Scene(vBox,450,350);
        Stage stage = new Stage();
        stage.setResizable(false);

        Label titleLabel = new Label("Title:");
        TextField titleTextField = new TextField();
        titleTextField.setMinWidth(375);
        HBox hBoxTextField = new HBox();
        hBoxTextField.getChildren().addAll(titleLabel,titleTextField);
        hBoxTextField.setAlignment(Pos.TOP_RIGHT);
        hBoxTextField.setSpacing(10);

        vBox.getChildren().add(hBoxTextField);


        comboBoxInit();
        Label priorityLabel = new Label("Priority:");
        HBox hBoxPriority = new HBox();
        comboBox.setMinWidth(375);
        hBoxPriority.getChildren().addAll(priorityLabel,comboBox);
        hBoxPriority.setAlignment(Pos.TOP_RIGHT);
        hBoxPriority.setSpacing(10);
        vBox.getChildren().add(hBoxPriority);



        HBox hBoxDate = new HBox();
        Label dateLabel = new Label("Date:");
        DatePicker dateField = new DatePicker();
        dateField.setMinWidth(375);
        hBoxDate.getChildren().addAll(dateLabel,dateField);
        hBoxDate.setSpacing(10);
        hBoxDate.setAlignment(Pos.TOP_RIGHT);
        vBox.getChildren().add(hBoxDate);



        TextArea textArea = new TextArea();
        HBox hBoxTextArea = new HBox();
        hBoxTextArea.getChildren().addAll(textArea);
        hBoxTextArea.setSpacing(10);
        hBoxTextArea.setAlignment(Pos.TOP_RIGHT);
        vBox.getChildren().add(hBoxTextArea);

        Button addTaskButton = null;
        if(adding)
            addTaskButton = new Button("Add");
        else if(editing) {
            addTaskButton = new Button("Edit");
            titleTextField.setText(editTask.title);
            textArea.setText(editTask.description);
            comboBox.setValue(editTask.priorityStatus);
            dateField.setValue(editTask.deadlineDate);
        }
        addTaskButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    Task tempTask;
                    LocalDate tempDate = dateField.getValue();
                    String tempTitle = titleTextField.getText();
                    String tempDescription = textArea.getText();
                    String tempStatus = (String) comboBox.getValue();
                    if (tempTitle != null && tempDescription != null && tempDate != null && tempStatus != null)
                    {
                        if(editing) {
                            if (toDoItems.contains(editTask)) {
                                int index = toDoItems.indexOf(editTask);
                                toDoItems.get(index).setTask(tempTitle,tempDescription,tempStatus,tempDate);
                            }
                            if (inProgressItems.contains(editTask)) {
                                int index = inProgressItems.indexOf(editTask);
                                inProgressItems.get(index).setTask(tempTitle,tempDescription,tempStatus,tempDate);
                            }
                            else if (doneItems.contains(editTask)) {
                                int index = doneItems.indexOf(editTask);
                                doneItems.get(index).setTask(tempTitle,tempDescription,tempStatus,tempDate);
                            }
                        }
                        else if(adding) {
                            tempTask = new Task(tempTitle, tempDescription, tempStatus, tempDate);
                            toDoItems.add(tempTask);
                            toDoList.setItems(toDoItems);
                        }
                        inProgressList.refresh();
                        toDoList.refresh();
                        doneList.refresh();
                        stage.close();
                    }

                    else {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning!");
                        alert.setHeaderText("Cannot add task!");
                        alert.setContentText("Every field has to be filled!");
                        alert.showAndWait();
                    }
                }
        });
        addTaskButton.setMinWidth(100);
        HBox hBoxButton = new HBox();
        hBoxButton.getChildren().add(addTaskButton);
        hBoxButton.setSpacing(10);
        hBoxButton.setAlignment(Pos.BOTTOM_CENTER);
        vBox.getChildren().add(hBoxButton);



        stage.setTitle("New task");
        stage.setScene(sceneButton);
        stage.show();
    }

    public void comboBoxInit() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Low Priority",
                        "Medium Priority",
                        "High Priority"
                );
        comboBox = new ComboBox(options);
        comboBox.setValue("Low Priority");
    }

    public void comboBoxIOInit() {
        ObservableList<String> options =
                FXCollections.observableArrayList("TO DO", "IN PROGRESS", "DONE","ALL");
        comboBoxIO = new ComboBox(options);
        comboBoxIO.setValue("TO DO");
        comboBoxIO.setPrefSize(180,30);
    }

    public void fileChooserInit(){
       fileChooser = new FileChooser();
       selectedFile=new File("ToDo.bin");
       serializeFilter = new FileChooser.ExtensionFilter("SERIALIZE files (*.txt,*.bin,...)", "*.txt","*.bin","*.ser");
       jsonFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
       csvFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
       fileChooser.getExtensionFilters().add(serializeFilter);
       fileChooser.getExtensionFilters().add(csvFilter);
       fileChooser.getExtensionFilters().add(jsonFilter);
    }

    public  ArrayList<Task> selectSerializationList() {
        ArrayList<Task> taskArrayList = new ArrayList<Task>();
        switch (comboBoxIO.getValue().toString()) {
            case "TO DO":
                for(Task task:toDoItems)
                    taskArrayList.add(task);
                break;
            case "IN PROGRESS":
                for(Task task:inProgressItems)
                    taskArrayList.add(task);
                break;
            case "DONE":
                for(Task task:doneItems)
                    taskArrayList.add(task);
                break;
            case "All":
                for(Task task:toDoItems)
                    taskArrayList.add(task);
                for(Task task:inProgressItems)
                    taskArrayList.add(task);
                for(Task task:doneItems)
                    taskArrayList.add(task);
                break;
        }
        return taskArrayList;
    }

    public  void selectDeserializationList(ArrayList<Task> taskArrayList) {
        switch (comboBoxIO.getValue().toString()) {
            case "TO DO":
                toDoItems.removeAll();
                toDoList.setItems(toDoItems);
                for (Task task:taskArrayList) {
                    toDoItems.add(task);
                }
                toDoList.setItems(toDoItems);
                toDoList.refresh();
                break;
            case "IN PROGRESS":
                inProgressItems.removeAll();
                inProgressList.setItems(inProgressItems);
                inProgressList.refresh();
                for (Task task:taskArrayList) {
                    inProgressItems.add(task);
                }
                inProgressList.setItems(inProgressItems);
                inProgressList.refresh();
                break;
            case "DONE":
                doneItems.removeAll();
                doneList.setItems(inProgressItems);
                doneItems.removeAll();
                for (Task task:taskArrayList) {
                    doneItems.add(task);
                }
                doneList.setItems(doneItems);
                doneList.refresh();
                break;
            case "ALL":
                toDoItems.removeAll();
                toDoList.setItems(toDoItems);
                for (Task task:taskArrayList) {
                    toDoItems.add(task);
                }
                toDoList.setItems(toDoItems);
                toDoList.refresh();

                inProgressItems.removeAll();
                inProgressList.setItems(inProgressItems);
                inProgressList.refresh();
                for (Task task:taskArrayList) {
                    inProgressItems.add(task);
                }
                inProgressList.setItems(inProgressItems);
                inProgressList.refresh();

                doneItems.removeAll();
                doneList.setItems(inProgressItems);
                doneItems.removeAll();
                for (Task task:taskArrayList) {
                    doneItems.add(task);
                }
                doneList.setItems(doneItems);
                doneList.refresh();
                break;
        }
    }

    public void save(){
        ArrayList<Task> taskArrayList=new ArrayList<Task>();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(selectedFile.getPath()))) {
            taskArrayList=selectSerializationList();
            outputStream.writeObject(taskArrayList);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Serialization was successful");
            alert.setContentText("The data was saved in "+selectedFile.getName());
            alert.showAndWait();
        } catch (IOException e) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Cannot serialize tasks");
            alert.setContentText("Incorrect function arguments - IOException");
            alert.showAndWait();
            e.printStackTrace();
            e.printStackTrace();
        }
    }

    public void load(){
        ArrayList<Task> taskArrayList=new ArrayList<Task>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(selectedFile.getPath()))) {
            selectDeserializationList((ArrayList<Task>) inputStream.readObject());

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Deserialization was successful");
            alert.setContentText("The data was load");
            alert.showAndWait();
        } catch (FileNotFoundException e) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Cannot load tasks");
            alert.setContentText("You have chosen the wrong file - FileNotFoundException");
            alert.showAndWait();
            e.printStackTrace();
        } catch (IOException e) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Cannot serialize tasks");
            alert.setContentText("Incorrect function arguments - IOException");
            alert.showAndWait();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Cannot serialize tasks");
            alert.setContentText("ClassNotFoundException");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

}
