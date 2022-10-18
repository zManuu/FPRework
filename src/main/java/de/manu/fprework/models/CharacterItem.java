package de.manu.fprework.models;

import de.manu.fprework.utils.javaef.Entity;

public class CharacterItem extends Entity {

    public int id;
    public int charId;
    public int itemId;
    public int amount;
    public int slot;

    public CharacterItem(int id, int charId, int itemId, int amount, int slot) {
        this.id = id;
        this.charId = charId;
        this.itemId = itemId;
        this.amount = amount;
        this.slot = slot;
    }

    public CharacterItem(int charId, int itemId, int amount, int slot) {
        this.charId = charId;
        this.itemId = itemId;
        this.amount = amount;
        this.slot = slot;
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
