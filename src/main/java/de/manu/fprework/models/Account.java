package de.manu.fprework.models;

import de.manu.fprework.utils.javaef.Entity;

public class Account extends Entity {

    public int id;
    public String name;
    public long uuid;

    public Account(int id, String name, long uuid) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
    }

    @Override
    public int getId() {
        return id;
    }
}
