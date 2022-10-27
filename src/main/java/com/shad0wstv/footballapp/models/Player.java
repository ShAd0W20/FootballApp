package com.shad0wstv.footballapp.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Player {

    private UUID uuid;
    private String name;
    private String surname;
    private int age;
    private Position position;
    private LocalDate end_contract;
    private Team team;

    public Player(UUID uuid, String name, String surname, int age, Position position, LocalDate end_contract, Team team) {
        this.uuid = uuid;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.position = position;
        this.end_contract = end_contract;
        this.team = team;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Position getPosition() {
        return position;
    }

    public String getPositionName() {
        return String.format("%s: %s", position.getName(), position.getSide());
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getEndContract() {
        return end_contract.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDate getEndContractDate() {
        return end_contract;
    }

    public void setEndContract(LocalDate end_contract) {
        this.end_contract = end_contract;
    }

    public Team getTeam() {
        return team;
    }

    public String getTeamName() {
        return team.getName();
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Player{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", position=" + position.getName() +
                ", end_contract=" + end_contract +
                ", team=" + team.getName() +
                '}';
    }
}
