package com.shad0wstv.footballapp.daoimpl;

import com.shad0wstv.footballapp.dao.TeamDAO;
import com.shad0wstv.footballapp.models.Team;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class TeamDAOImpl extends ConnectionDAOImpl implements TeamDAO {

    /**
     * Gets all teams from the database
     *
     * @return ArrayList<Team>
     */
    @Override
    public ArrayList<Team> getAllTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        try {
            PreparedStatement stmt = this.connect().prepareStatement("SELECT * FROM team");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                teams.add(new Team(UUID.fromString(rs.getString("uuid")), rs.getString("name")));
            }

        } catch (SQLException e) {
            return null;
        }
        return teams;
    }

    /**
     * Gets a team by UUID
     *
     * @param uuid The UUID of the team to get
     * @return Team
     */
    @Override
    public Team getTeamByUUID(@NotNull UUID uuid) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement("SELECT * FROM team WHERE uuid = ?");
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Team(UUID.fromString(rs.getString("uuid")), rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a team by name
     *
     * @param name The name of the team to get
     * @return Team
     */
    @Override
    public Team getTeamByName(String name) {
        return getAllTeams().stream()
                .filter(team -> team.getName().toLowerCase().contains(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a new team to the database
     *
     * @param team The team to add
     */
    @Override
    public void addTeam(@NotNull Team team) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement("INSERT INTO team (uuid, name) VALUES (?, ?)");
            stmt.setString(1, team.getUuid().toString());
            stmt.setString(2, team.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates a team in the database
     *
     * @param team The team to update
     */
    @Override
    public void updateTeam(@NotNull Team team) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement("UPDATE team SET name = ? WHERE uuid = ?");
            stmt.setString(1, team.getName());
            stmt.setString(2, team.getUuid().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a team from the database
     *
     * @param team The team to delete
     */
    @Override
    public void deleteTeam(Team team) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement("DELETE FROM team WHERE uuid = ?");
            stmt.setString(1, team.getUuid().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
