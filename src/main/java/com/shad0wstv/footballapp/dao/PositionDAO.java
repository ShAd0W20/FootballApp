package com.shad0wstv.footballapp.dao;

import com.shad0wstv.footballapp.models.Position;

import java.util.ArrayList;
import java.util.UUID;

public interface PositionDAO {
    ArrayList<Position> getAllPositions();
    Position getPositionByUUID(UUID uuid);
    Position getPositionByName(String name);
    Position getPositionByNameAndSide(String name, String side);
    void addPosition(Position position);
    void updatePosition(Position position);
    void deletePosition(Position position);
}
