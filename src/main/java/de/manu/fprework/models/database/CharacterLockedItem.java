package de.manu.fprework.models.database;

import de.manu.fprework.utils.javaef.Entity;

public class CharacterLockedItem extends Entity {

    public int id;
    public int charId;
    public String itemDisplayName;

    public CharacterLockedItem(int id, int charId, String itemDisplayName) {
        this.id = id;
        this.charId = charId;
        this.itemDisplayName = itemDisplayName;
    }

    public CharacterLockedItem(int charId, String itemDisplayName) {
        this.charId = charId;
        this.itemDisplayName = itemDisplayName;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
