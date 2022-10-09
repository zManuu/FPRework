package de.manu.fprework.models;

import de.manu.fprework.utils.javaef.Entity;

public class Character extends Entity {

    public int id;
    public String name;
    public int profession;
    public int level;
    public int xp;

    public Character(int id, String name, int profession, int level, int xp) {
        this.id = id;
        this.name = name;
        this.profession = profession;
        this.level = level;
        this.xp = xp;
    }

    @Override
    public int getId() {
        return id;
    }
}
