package com.shad0wstv.footballapp.dao;

import com.shad0wstv.footballapp.models.Team;

import java.util.ArrayList;
import java.util.UUID;

public interface TeamDAO {
    ArrayList<Team> getAllTeams();
    Team getTeamByUUID(UUID uuid);
    Team getTeamByName(String name);
    void addTeam(Team team);
    void updateTeam(Team team);
    void deleteTeam(Team team);
}
