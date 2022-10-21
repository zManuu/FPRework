package de.manu.fprework.models.database;

import de.manu.fprework.utils.javaef.Entity;

public class ServerItemStatsWeapon extends Entity {

    public int id;
    public int itemId;
    public float damage;
    public float attackSpeed;
    public boolean bowInfinitive;

    public ServerItemStatsWeapon(int id, int itemId, float damage, float attackSpeed, boolean bowInfinitive) {
        this.id = id;
        this.itemId = itemId;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.bowInfinitive = bowInfinitive;
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
