package com.shad0wstv.footballapp;

import com.shad0wstv.footballapp.controllers.ScreenController;
import com.shad0wstv.footballapp.daoimpl.PlayerDAOImpl;
import com.shad0wstv.footballapp.daoimpl.PositionDAOImpl;
import com.shad0wstv.footballapp.daoimpl.TeamDAOImpl;
import com.shad0wstv.footballapp.models.Player;
import com.shad0wstv.footballapp.models.Position;
import com.shad0wstv.footballapp.models.Team;
import com.shad0wstv.footballapp.utils.NotifyAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class MainController {
    @FXML
    private TextField inputName;

    @FXML
    private TextField inputSurname;

    @FXML
    private TextField inputAge;

    @FXML
    private ChoiceBox<Position> selectPosition;

    @FXML
    private DatePicker choseDateEndContract;

    @FXML
    private ChoiceBox<Team> selectTeam;

    @FXML
    private TextField inputSearch;

    @FXML
    private Button saveBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableView<Player> tableViewPlayers;

    /**
     * Initialize the main window here we set the values for the table and the choice boxes
     */
    public void initialize() {
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);

        populateTableView();
        populatePositionsChoiceBox();
        populateTeamsChoiceBox();

        tableViewPlayers.setOnMouseClicked(this::displaySelectedPlayer);

        inputSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                populateTableView();
            } else {
                searchInTable(newValue);
            }
        });
    }


    /**
     * For saving a new player in the database triggered by the save button on the UI onClick
     */
    @FXML
    private void saveNewPlayer() {
        if (!validateData()) return;

        new PlayerDAOImpl().addPlayer(
                new Player(UUID.randomUUID(),
                        inputName.getText(),
                        inputSurname.getText(),
                        Integer.parseInt(inputAge.getText()),
                        new Position(selectPosition.getValue().getUuid(), selectPosition.getValue().getName(), selectPosition.getValue().getSide()),
                        choseDateEndContract.getValue(),
                        new Team(selectTeam.getValue().getUuid(), selectTeam.getValue().getName())
                )
        );
        populateTableView();
        clearInputFields();
    }

    /**
     * To clear the input fields as well as the selected item from the table
     */
    @FXML
    private void clearInputFields() {
        inputName.clear();
        inputSurname.clear();
        inputAge.clear();
        selectPosition.setValue(null);
        choseDateEndContract.setValue(null);
        selectTeam.setValue(null);
        tableViewPlayers.getSelectionModel().clearSelection();
        resetButtons();
    }

    @FXML
    private void switchToTeamsView(ActionEvent event) throws Exception {
        new ScreenController("teams-view.fxml").activate(event);
    }

    @FXML
    private void switchToPositionsView(ActionEvent event) throws Exception {
        new ScreenController("position-view.fxml").activate(event);
    }

    /**
     * Search in the table by name or surname of the player
     * @param searchValue the value to search
     * */
    private void searchInTable(String searchValue) {
        Player player = new PlayerDAOImpl().getPlayerByNameOrSurname(searchValue);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        ObservableList<Player> observableList = FXCollections.observableArrayList(players);
        tableViewPlayers.setItems(observableList);
    }

    /**
     * Display the selected player from the table in the input fields and enable update and delete buttons
     *
     * @param event the event of the mouse click
     */
    private void displaySelectedPlayer(@NotNull MouseEvent event) {
        if (event.getClickCount() == 1) {
            saveBtn.setDisable(true);
            updateBtn.setDisable(false);
            deleteBtn.setDisable(false);
            event.consume();

            Player player = selectedPlayerFromTable();

            if (player == null) return;

            deleteBtn.setOnMouseClicked(e -> new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this player?", ButtonType.YES, ButtonType.NO).showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.YES) {
                    deletePlayer(player);
                    tableViewPlayers.getSelectionModel().clearSelection();
                }
            }));

            updateBtn.setOnMouseClicked(e -> {
                updatePlayer(player);
                tableViewPlayers.getSelectionModel().clearSelection();
            });
        }
    }

    /**
     * Get the data from the database based on the selected player from the table
     *
     * @return the selected player
     */
    private Player selectedPlayerFromTable() {
        Player player = tableViewPlayers.getSelectionModel().getSelectedItem();
        if (player != null) {
            inputName.setText(player.getName());
            inputSurname.setText(player.getSurname());
            inputAge.setText(String.valueOf(player.getAge()));
            selectPosition.setValue(player.getPosition());
            choseDateEndContract.setValue(player.getEndContractDate());
            selectTeam.setValue(player.getTeam());
            return player;
        }
        return null;
    }

    /**
     * Update a player data in the database
     *
     * @param player the player to be updated
     */
    private void updatePlayer(@NotNull Player player) {
        if(!validateData()) return;
        player.setName(inputName.getText());
        player.setSurname(inputSurname.getText());
        player.setAge(Integer.parseInt(inputAge.getText()));
        player.setPosition(selectPosition.getValue());
        player.setEndContract(choseDateEndContract.getValue());
        player.setTeam(selectTeam.getValue());
        new PlayerDAOImpl().updatePlayer(player);
        clearInputFields();
        resetButtons();
        populateTableView();
    }

    /**
     * Delete a player from the database
     *
     * @param player the player to delete
     */
    private void deletePlayer(@NotNull Player player) {
        new PlayerDAOImpl().deletePlayer(player);
        clearInputFields();
        resetButtons();
        populateTableView();
    }

    /**
     * To populate the tableview with the data from the database
     */
    private void populateTableView() {
        ArrayList<Player> players = new PlayerDAOImpl().getAllPlayers();
        if (players == null) return;
        ObservableList<Player> observableList = FXCollections.observableArrayList(players);
        tableViewPlayers.getColumns().forEach(column -> {
            column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
            column.setStyle("-fx-alignment: CENTER;");
        });
        tableViewPlayers.setItems(observableList);
    }

    /**
     * To populate the positions choice box with the data from the database
     */
    private void populatePositionsChoiceBox() {
        ArrayList<Position> positions = new PositionDAOImpl().getAllPositions();
        if (positions == null) return;
        ObservableList<Position> ObPositions = FXCollections.observableArrayList(positions);
        selectPosition.setItems(ObPositions);
        selectPosition.getItems().forEach(position -> selectPosition.converterProperty().set(new StringConverter<>() {
            @Override
            public String toString(Position object) {
                if (object != null) {
                    return object.getName() + ": " + object.getSide();
                }
                return null;
            }

            @Override
            public Position fromString(String string) {
                return null;
            }
        }));
    }

    /**
     * To populate the teams choice box with the data from the database
     */
    private void populateTeamsChoiceBox() {
        ArrayList<Team> teams = new TeamDAOImpl().getAllTeams();
        if (teams == null) return;
        ObservableList<Team> ObTeams = FXCollections.observableArrayList(teams);
        selectTeam.setItems(ObTeams);
        selectTeam.getItems().forEach(team -> selectTeam.converterProperty().set(new StringConverter<>() {
            @Override
            public String toString(Team object) {
                if (object != null) {
                    return object.getName();
                }
                return null;
            }

            @Override
            public Team fromString(String s) {
                return null;
            }
        }));
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
        if (inputName.getText().isEmpty() || inputSurname.getText().isEmpty() || inputAge.getText().isEmpty() || selectPosition.getValue() == null || choseDateEndContract.getValue() == null || selectTeam.getValue() == null) {
            NotifyAlert.show(Alert.AlertType.ERROR, "Error", "Error", "Please fill all fields");
            return false;
        }
        if (choseDateEndContract.getValue().isBefore(LocalDate.now())) {
            NotifyAlert.show(Alert.AlertType.ERROR, "Error", "Date error", "Date must be after today");
            return false;
        }

        try {
            int age = Integer.parseInt(inputAge.getText());
            if (age < 0) {
                NotifyAlert.show(Alert.AlertType.ERROR, "Error", "Age error", "Age must be positive");
                return false;
            }
            if (age > 100) {
                NotifyAlert.show(Alert.AlertType.ERROR, "Error", "Age error", "Age must be less than 100");
                return false;
            }
        } catch (NumberFormatException e) {
            NotifyAlert.show(Alert.AlertType.ERROR, "Error", "Age error", "Age must be a number");
            return false;
        }
        return true;
    }
}
