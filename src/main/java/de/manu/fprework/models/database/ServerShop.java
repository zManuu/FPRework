package de.manu.fprework.models.database;

import de.manu.fprework.utils.javaef.Entity;

public class ServerShop extends Entity {

    public int id;
    public String displayName;
    public float posX;
    public float posY;
    public float posZ;
    public float pedRotation;

    public ServerShop(int id, String displayName, float posX, float posY, float posZ, float pedRotation) {
        this.id = id;
        this.displayName = displayName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.pedRotation = pedRotation;
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
