package com.shad0wstv.footballapp.models;

import java.util.UUID;

public class Position {
    private UUID uuid;
    private String name;
    private String side;

    public Position(UUID uuid, String name, String side) {
        this.uuid = uuid;
        this.name = name;
        this.side = side;
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

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    @Override
    public String toString() {
        return "Position{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", side='" + side + '\'' +
                '}';
    }
}
