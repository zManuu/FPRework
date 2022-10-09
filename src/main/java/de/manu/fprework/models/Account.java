package de.manu.fprework.models;

import de.manu.fprework.utils.javaef.Entity;

public class Account extends Entity {

    public int id;
    public String name;
    public String uuid;

    public Account(int id, String name, String uuid) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
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
}
