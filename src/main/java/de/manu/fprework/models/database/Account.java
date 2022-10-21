package de.manu.fprework.models.database;

import de.manu.fprework.utils.javaef.Entity;

public class Account extends Entity {

    public int id;
    public String name;
    public String uuid;
    public int selectedChar;

    public Account(int id, String name, String uuid, int selectedChar) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
        this.selectedChar = selectedChar;
    }

    public Account(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setSelectedChar(int selectedChar) {
        this.selectedChar = selectedChar;
    }
}
