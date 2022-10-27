package com.shad0wstv.footballapp.views;

import com.shad0wstv.footballapp.controllers.ScreenController;
import com.shad0wstv.footballapp.daoimpl.TeamDAOImpl;
import com.shad0wstv.footballapp.models.Team;
import com.shad0wstv.footballapp.utils.NotifyAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class TeamViewController {
    @FXML
    private TextField inputName;

    @FXML
    private TextField inputSearch;

    @FXML
    private Button saveBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableView<Team> tableViewTeams;

    /**
     * Initialize the main window here we set the values for the table and the choice boxes
     */
    public void initialize() {
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);

        populateTableView();

        tableViewTeams.setOnMouseClicked(this::displaySelectedTeam);

        inputSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                populateTableView();
            } else {
                searchInTable(newValue);
            }
        });
    }


    /**
     * For saving a new team in the database triggered by the save button on the UI onClick
     */
    @FXML
    private void saveNewTeam() {
        if (!validateData()) return;

        new TeamDAOImpl().addTeam(new Team(UUID.randomUUID(), inputName.getText()));
        populateTableView();
        clearInputFields();
    }

    /**
     * To clear the input fields as well as the selected item from the table
     */
    @FXML
    private void clearInputFields() {
        inputName.clear();
        tableViewTeams.getSelectionModel().clearSelection();
        resetButtons();
    }

    @FXML
    private void switchToHomeView(ActionEvent event) throws Exception {
        new ScreenController("main-view.fxml").activate(event);
    }

    @FXML
    private void switchToPositionsView(ActionEvent event) throws Exception {
        new ScreenController("position-view.fxml").activate(event);
    }

    /**
     * Search in the table by name or surname of the team
     *
     * @param searchValue the value to search
     */
    private void searchInTable(String searchValue) {
        Team team = new TeamDAOImpl().getTeamByName(searchValue);
        ArrayList<Team> teams = new ArrayList<>();
        teams.add(team);
        ObservableList<Team> observableList = FXCollections.observableArrayList(teams);
        tableViewTeams.setItems(observableList);
    }

    /**
     * Display the selected team from the table in the input fields and enable update and delete buttons
     *
     * @param event the event of the mouse click
     */
    private void displaySelectedTeam(@NotNull MouseEvent event) {
        if (event.getClickCount() == 1) {
            saveBtn.setDisable(true);
            updateBtn.setDisable(false);
            deleteBtn.setDisable(false);
            event.consume();

            Team team = tableViewTeams.getSelectionModel().getSelectedItem();

            if (team == null) return;
            inputName.setText(team.getName());

            deleteBtn.setOnMouseClicked(e ->
                    new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this team?", ButtonType.YES, ButtonType.NO).showAndWait().ifPresent(buttonType -> {
                        if (buttonType == ButtonType.YES) {
                            deleteTeam(team);
                            tableViewTeams.getSelectionModel().clearSelection();
                        }
                    })
            );

            updateBtn.setOnMouseClicked(e -> {
                updateTeam(team);
                tableViewTeams.getSelectionModel().clearSelection();
            });
        }
    }

    /**
     * Update a team data in the database
     *
     * @param team the team to be updated
     */
    private void updateTeam(@NotNull Team team) {
        if (!validateData()) return;
        team.setName(inputName.getText());
        new TeamDAOImpl().updateTeam(team);
        clearInputFields();
        resetButtons();
        populateTableView();
    }

    /**
     * Delete a team from the database
     *
     * @param team the team to delete
     */
    private void deleteTeam(@NotNull Team team) {
        new TeamDAOImpl().deleteTeam(team);
        clearInputFields();
        resetButtons();
        populateTableView();
    }

    /**
     * To populate the tableview with the data from the database
     */
    private void populateTableView() {
        ArrayList<Team> teams = new TeamDAOImpl().getAllTeams();
        if (teams == null) return;
        ObservableList<Team> observableList = FXCollections.observableArrayList(teams);
        tableViewTeams.getColumns().forEach(column -> {
            column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
            column.setStyle("-fx-alignment: CENTER;");
        });
        tableViewTeams.setItems(observableList);
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
        Team team = new TeamDAOImpl().getTeamByName(inputName.getText());
        if (team != null) {
            NotifyAlert.show(Alert.AlertType.ERROR, "Error", "Error", "Team already exists");
            return false;
        }
        return true;
    }
}
