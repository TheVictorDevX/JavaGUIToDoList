package com.example.todolistapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.geometry.Insets;

public class TodoListApp extends Application {

    private TextField taskInput;
    private Button addButton;
    private Button deleteButton;
    private ListView<Task> taskListView;
    private ObservableList<Task> tasks;

    @Override
    public void start(Stage primaryStage) {
        tasks = FXCollections.observableArrayList();

        taskInput = new TextField();
        taskInput.setPromptText("Enter new task");

        addButton = new Button("Add");
        deleteButton = new Button("Delete");

        taskListView = new ListView<>(tasks);
        taskListView.setCellFactory(param -> new ListCell<Task>() {
            private final CheckBox checkBox = new CheckBox();
            private Task currentTask;

            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (currentTask != null) {
                    checkBox.selectedProperty().unbindBidirectional(currentTask.completedProperty());
                }

                if (empty || task == null) {
                    currentTask = null;
                    setGraphic(null);
                } else {
                    currentTask = task;
                    checkBox.setText(task.getDescription());
                    checkBox.selectedProperty().bindBidirectional(task.completedProperty());
                    setGraphic(checkBox);
                }
            }
        });


        addButton.setOnAction(event -> {
            String taskText = taskInput.getText().trim();
            if (!taskText.isEmpty()) {
                tasks.add(new Task(taskText));
                taskInput.clear();
            }
        });

        deleteButton.setOnAction(event -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                tasks.remove(selectedTask);
            }
        });

        HBox inputAndButtons = new HBox(10, taskInput, addButton);
        VBox root = new VBox(10, inputAndButtons, deleteButton, taskListView);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 350, 450);
        primaryStage.setTitle("To-Do List");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
