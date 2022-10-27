package com.shad0wstv.footballapp.daoimpl;

import com.shad0wstv.footballapp.dao.PositionDAO;
import com.shad0wstv.footballapp.models.Position;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class PositionDAOImpl extends ConnectionDAOImpl implements PositionDAO {

    /**
     * Gets all positions from the database
     *
     * @return ArrayList<Position>
     */
    @Override
    public ArrayList<Position> getAllPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        try {
            PreparedStatement stmt = this.connect().prepareStatement("SELECT * FROM position");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                positions.add(new Position(UUID.fromString(rs.getString("uuid")), rs.getString("name"), rs.getString("site")));
            }

        } catch (SQLException e) {
            return null;
        }
        return positions;
    }

    /**
     * Gets a position by UUID
     *
     * @param uuid The UUID of the position to get
     * @return Position
     */
    @Override
    public Position getPositionByUUID(@NotNull UUID uuid) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement("SELECT * FROM position WHERE uuid = ?");
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Position(UUID.fromString(rs.getString("uuid")), rs.getString("name"), rs.getString("site"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a position by name
     *
     * @param name The name of the position to get
     * @return Position
     */
    @Override
    public Position getPositionByName(String name) {
        return getAllPositions().stream()
                .filter(position -> position.getName().toLowerCase().contains(name.toLowerCase()))
                .findAny()
                .orElse(null);
    }

    @Override
    public Position getPositionByNameAndSide(String name, String side) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement("SELECT * FROM position WHERE site = ? AND name = ?");
            stmt.setString(1, side);
            stmt.setString(2, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Position(UUID.fromString(rs.getString("uuid")), rs.getString("name"), rs.getString("site"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param position The position to add to the database
     */
    @Override
    public void addPosition(Position position) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement("INSERT INTO position (uuid, name, site) VALUES (?, ?, ?)");
            stmt.setString(1, position.getUuid().toString());
            stmt.setString(2, position.getName());
            stmt.setString(3, position.getSide());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates a position in the database
     *
     * @param position The position to update
     */
    @Override
    public void updatePosition(@NotNull Position position) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement("UPDATE position SET name = ?, site = ? WHERE uuid = ?");
            stmt.setString(1, position.getName());
            stmt.setString(2, position.getSide());
            stmt.setString(3, position.getUuid().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a position from the database
     *
     * @param position The position to delete
     */
    @Override
    public void deletePosition(@NotNull Position position) {
        try {
            PreparedStatement stmt = this.connect().prepareStatement("DELETE FROM position WHERE uuid = ?");
            stmt.setString(1, position.getUuid().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
