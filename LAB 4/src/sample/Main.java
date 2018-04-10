package sample;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.util.Callback;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private MenuItem exitItem;
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
    private ComboBox comboBox;
    private BorderPane layoutPane;
    private Scene scene;
    private Task editTask;


    @Override
    public void start(Stage primaryStage) throws Exception{
        layoutPane = new BorderPane();
        scene = new Scene(layoutPane, 500,530);
        primaryStage.setResizable(false);

        menuInit();
        listInit();
        buttonInit();
        contextMenuInit();
        dragAndDropInit();
        tooltipInit();
        primaryStage.setTitle("Kanban");
        primaryStage.setScene(scene);
        primaryStage.show();
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
        menuAbout = new Menu("");
        menuFile.getItems().add(exitItem);
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

    }

    public void listInit() {
        /* To-Do list init */
        toDoList = new ListView<>();
        toDoItems = FXCollections.<Task>observableArrayList();
        toDoList.setItems(toDoItems);

        toDoList.setPrefSize(155,375); //150 / 100
        toDoList.setEditable(true);
        layoutPane.setLeft(toDoList);
        Label toDoLabel = new Label("TO DO");
        VBox vboxToDo = new VBox();
        vboxToDo.getChildren().addAll(toDoLabel,toDoList);
        vboxToDo.setAlignment(Pos.CENTER);
        layoutPane.setLeft(vboxToDo);



        inProgressList = new ListView<>();
        inProgressItems = FXCollections.observableArrayList();

        inProgressList.setItems(inProgressItems);
        inProgressList.setPrefSize(155,375);
        inProgressList.setEditable(true);
        Label inProgressLabel = new Label("IN PROGRES");
        VBox vboxInProgress = new VBox();
        vboxInProgress.getChildren().addAll(inProgressLabel,inProgressList);
        vboxInProgress.setAlignment(Pos.CENTER);
        layoutPane.setCenter(vboxInProgress);

        doneList = new ListView<>();
        doneItems = FXCollections.observableArrayList();

        doneList.setItems(doneItems);
        doneList.setPrefSize(155,375);
        doneList.setEditable(true);
        Label doneLabel = new Label("DONE");
        VBox vboxDone = new VBox();
        vboxDone.getChildren().addAll(doneLabel,doneList);
        vboxDone.setAlignment(Pos.CENTER);
        layoutPane.setRight(vboxDone);

    }

    public void buttonInit() {
        button = new Button("ADD NEW TASK");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean adding = true;
                buttonPressed(adding,false);
            }
        });
        layoutPane.setBottom(button);
        layoutPane.setAlignment(button, Pos.CENTER);
        layoutPane.setMargin(button,new Insets(10,10,20,10));
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
                        Alert alert = new Alert(Alert.AlertType.WARNING);
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
}
