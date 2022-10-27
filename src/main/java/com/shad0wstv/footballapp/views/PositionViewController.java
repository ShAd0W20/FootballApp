package com.shad0wstv.footballapp.views;

import com.shad0wstv.footballapp.controllers.ScreenController;
import com.shad0wstv.footballapp.daoimpl.PositionDAOImpl;
import com.shad0wstv.footballapp.models.Position;
import com.shad0wstv.footballapp.utils.NotifyAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.UUID;

public class PositionViewController {
    @FXML
    private TextField inputName;

    @FXML
    private TextField inputPositionSite;

    @FXML
    private TextField inputSearch;

    @FXML
    private Button saveBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableView<Position> tableViewPositions;

    public void initialize() {
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);

        populateTableView();

        tableViewPositions.setOnMouseClicked(this::displaySelectedPosition);

        inputSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                populateTableView();
            } else {
                searchInTable(newValue);
            }
        });
    }

    @FXML
    private void saveNewPosition() {
        if(!validateData()) return;

        new PositionDAOImpl().addPosition(new Position(UUID.randomUUID(), inputName.getText(), inputPositionSite.getText()));
        populateTableView();
        clearInputFields();
        resetButtons();
    }

    @FXML
    private void clearInputFields() {
        inputName.clear();
        inputPositionSite.clear();
    }

    @FXML
    private void switchToHomeView(ActionEvent event) throws Exception {
        new ScreenController("main-view.fxml").activate(event);
    }

    @FXML
    private void switchToTeamView(ActionEvent event) throws Exception {
        new ScreenController("teams-view.fxml").activate(event);
    }

    /**
     * Search in the table by name or surname of the position
     *
     * @param searchValue the value to search
     */
    private void searchInTable(String searchValue) {
        Position position = new PositionDAOImpl().getPositionByName(searchValue);
        ArrayList<Position> positions = new ArrayList<>();
        positions.add(position);
        ObservableList<Position> observableList = FXCollections.observableArrayList(positions);
        tableViewPositions.setItems(observableList);
    }

    /**
     * Display the selected position from the table in the input fields and enable update and delete buttons
     *
     * @param event the event of the mouse click
     */
    private void displaySelectedPosition(MouseEvent event) {
        if (event.getClickCount() == 1) {
            saveBtn.setDisable(true);
            updateBtn.setDisable(false);
            deleteBtn.setDisable(false);
            event.consume();

            Position position = selectedPositionFromTable();

            if (position == null) return;

            deleteBtn.setOnMouseClicked(e -> new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this position?", ButtonType.YES, ButtonType.NO).showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.YES) {
                    deletePlayer(position);
                    tableViewPositions.getSelectionModel().clearSelection();
                }
            }));

            updateBtn.setOnMouseClicked(e -> {
                updatePosition(position);
                tableViewPositions.getSelectionModel().clearSelection();
            });
        }
    }

    /**
     * Get the data from the database based on the selected position from the table
     *
     * @return the selected position
     */
    private Position selectedPositionFromTable() {
        Position position = tableViewPositions.getSelectionModel().getSelectedItem();
        if (position != null) {
            inputName.setText(position.getName());
            inputPositionSite.setText(position.getSide());
            return position;
        }
        return null;
    }

    /**
     * Update a position data in the database
     *
     * @param position the position to be updated
     */
    private void updatePosition(Position position) {
        if (!validateData()) return;
        position.setName(inputName.getText());
        new PositionDAOImpl().updatePosition(position);
        clearInputFields();
        resetButtons();
        populateTableView();
    }

    /**
     * Delete a position from the database
     *
     * @param position the position to delete
     */
    private void deletePlayer(Position position) {
        new PositionDAOImpl().deletePosition(position);
        clearInputFields();
        resetButtons();
        populateTableView();
    }

    /**
     * To populate the tableview with the data from the database
     */
    private void populateTableView() {
        ArrayList<Position> positions = new PositionDAOImpl().getAllPositions();
        if (positions == null) return;
        ObservableList<Position> observableList = FXCollections.observableArrayList(positions);
        tableViewPositions.getColumns().forEach(column -> {
            column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
            column.setStyle("-fx-alignment: CENTER;");
        });
        tableViewPositions.setItems(observableList);
    }

    /**
     * To reset the buttons to their default state
     */
    private void resetButtons() {
        saveBtn.setDisable(false);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    /**
     * To validate the data from the input fields
     *
     * @return true if the data is valid, false if not
     */
    private Boolean validateData() {
        if (inputName.getText().isEmpty()) {
            NotifyAlert.show(Alert.AlertType.ERROR, "Error", "Error", "Please fill all fields");
            return false;
        }
        Position position = new PositionDAOImpl().getPositionByNameAndSide(inputName.getText(), inputPositionSite.getText());
        if (position != null) {
            NotifyAlert.show(Alert.AlertType.ERROR, "Error", "Error", "This position already exists");
            return false;
        }
        return true;
    }
}
