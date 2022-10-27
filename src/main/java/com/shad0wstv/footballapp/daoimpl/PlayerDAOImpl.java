package com.shad0wstv.footballapp.daoimpl;

import com.shad0wstv.footballapp.dao.PlayerDAO;
import com.shad0wstv.footballapp.models.Player;
import com.shad0wstv.footballapp.models.Position;
import com.shad0wstv.footballapp.models.Team;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerDAOImpl extends ConnectionDAOImpl implements PlayerDAO {

    /**
     * Gets all players from the database
     * @return ArrayList<Player>
     * */
    @Override
    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        try {
            PreparedStatement stmt = this.connect().prepareStatement(
                    "SELECT p.uuid, p.name, p.surname, p.age, p.end_contract, pos.uuid as pos_uuid, pos.name as pos_name, pos.site, t.uuid as team_uuid, t.name as team_name " +
                            "FROM player p " +
                            "INNER JOIN position pos ON p.position = pos.uuid " +
                            "INNER JOIN team t ON p.team = t.uuid;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Player player = new Player(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age"),
                        new Position(
                                UUID.fromString(rs.getString("pos_uuid")),
                                rs.getString("pos_name"),
                                rs.getString("site")
                        ),
                        rs.getDate("end_contract").toLocalDate(),
                        new Team(
                                UUID.fromString(rs.getString("team_uuid")),
                                rs.getString("team_name")
                        )
                );
                players.add(player);
            }
        } catch (SQLException e) {
            return null;
        }
        return players;
    }

    /**
     * Gets a player by UUID
     * @param uuid The UUID of the player to get
     * @return Player
     * */
    @Override
    public Player getPlayerByUUID(String uuid) {

        try {
            PreparedStatement stmt = this.connect().prepareStatement(
                    "SELECT p.uuid, p.name, p.surname, p.age, p.end_contract, pos.uuid as pos_uuid, pos.name as pos_name, pos.site, t.uuid as team_uuid, t.name as team_name " +
                            "FROM player p " +
                            "INNER JOIN position pos ON p.position = pos.uuid " +
                            "INNER JOIN team t ON p.team = t.uuid " +
                            "WHERE p.uuid = ?;");
            stmt.setString(1, uuid);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Player(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age"),
                        new Position(
                                UUID.fromString(rs.getString("pos_uuid")),
                                rs.getString("pos_name"),
                                rs.getString("site")
                        ),
                        rs.getDate("end_contract").toLocalDate(),
                        new Team(
                                UUID.fromString(rs.getString("team_uuid")),
                                rs.getString("team_name")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a player by its name or surname
     * @param string The name or surname of the player to get
     * @return Player
     * */
    @Override
    public Player getPlayerByNameOrSurname(String string) {
        return getAllPlayers().stream()
                .filter(player ->
                        player.getName().toLowerCase().contains(string.toLowerCase()) || player.getSurname().toLowerCase().contains(string.toLowerCase())
                )
                .findFirst().orElse(null);
    }

    /**
     * Adds a player to the database
     * @param player The player to add
     * */
    @Override
    public void addPlayer(@NotNull Player player) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(
                    "INSERT INTO player (uuid, name, surname, age, position, end_contract, team) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?);");
            stmt.setString(1, player.getUuid().toString());
            stmt.setString(2, player.getName());
            stmt.setString(3, player.getSurname());
            stmt.setInt(4, player.getAge());
            stmt.setString(5, player.getPosition().getUuid().toString());
            stmt.setDate(6, Date.valueOf(player.getEndContractDate()));
            stmt.setString(7, player.getTeam().getUuid().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a player in the database
     * @param player The player to update
     * */
    @Override
    public void updatePlayer(@NotNull Player player) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(
                    "UPDATE player " +
                            "SET name = ?, surname = ?, age = ?, position = ?, end_contract = ?, team = ? " +
                            "WHERE uuid = ?;");
            stmt.setString(1, player.getName());
            stmt.setString(2, player.getSurname());
            stmt.setInt(3, player.getAge());
            stmt.setString(4, player.getPosition().getUuid().toString());
            stmt.setDate(5, Date.valueOf(player.getEndContractDate()));
            stmt.setString(6, player.getTeam().getUuid().toString());
            stmt.setString(7, player.getUuid().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a player from the database
     * @param player The player to delete
     * */
    @Override
    public void deletePlayer(@NotNull Player player) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement(
                    "DELETE FROM player " +
                            "WHERE uuid = ?;");
            stmt.setString(1, player.getUuid().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
