package de.manu.fprework.models.database;

import de.manu.fprework.utils.javaef.Entity;

public class ServerItemStatsConsumable extends Entity {

    public int id;
    public int itemId;
    public int hunger;
    public int hearts;
    public String effectName;
    public int effectStrength;
    public int effectDuration;

    public ServerItemStatsConsumable(int id, int itemId, int hunger, int hearts, String effectName, int effectStrength, int effectDuration) {
        this.id = id;
        this.itemId = itemId;
        this.hunger = hunger;
        this.hearts = hearts;
        this.effectName = effectName;
        this.effectStrength = effectStrength;
        this.effectDuration = effectDuration;
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
