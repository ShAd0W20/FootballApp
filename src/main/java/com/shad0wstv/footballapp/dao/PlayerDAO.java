package com.shad0wstv.footballapp.dao;

import com.shad0wstv.footballapp.models.Player;

import java.util.ArrayList;

public interface PlayerDAO {
    ArrayList<Player> getAllPlayers();
    Player getPlayerByUUID(String uuid);
    Player getPlayerByNameOrSurname(String search);
    void addPlayer(Player player);
    void updatePlayer(Player player);
    void deletePlayer(Player player);
}
