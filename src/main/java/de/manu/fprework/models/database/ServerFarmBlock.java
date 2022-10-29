package de.manu.fprework.models.database;

import de.manu.fprework.utils.javaef.Entity;

public class ServerFarmBlock extends Entity {

    public int id;
    public int posX;
    public int posY;
    public int posZ;
    public int itemId;

    public ServerFarmBlock(int id, int posX, int posY, int posZ, int itemId) {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.itemId = itemId;
    }

    public ServerFarmBlock(int posX, int posY, int posZ, int itemId) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.itemId = itemId;
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
