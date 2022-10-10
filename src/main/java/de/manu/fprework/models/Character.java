package de.manu.fprework.models;

import de.manu.fprework.utils.javaef.Entity;

public class Character extends Entity {

    public int id;
    public int accountId;
    public int characterClass;
    public int level;
    public int xp;

    public Character(int id, int accountId, int characterClass, int level, int xp) {
        this.id = id;
        this.accountId = accountId;
        this.characterClass = characterClass;
        this.level = level;
        this.xp = xp;
    }

    public Character(int accountId, int characterClass, int level, int xp) {
        this.accountId = accountId;
        this.characterClass = characterClass;
        this.level = level;
        this.xp = xp;
    }

    @Override
    public int getId() {
        return id;
    }

}
