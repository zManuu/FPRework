package de.manu.fprework.models;

import de.manu.fprework.utils.javaef.Entity;

public class ServerItem extends Entity {

    public int id;
    public int type;
    public String displayName;
    public String material;

    public ServerItem(int type, String displayName, String material) {
        this.type = type;
        this.displayName = displayName;
        this.material = material;
    }

    public ServerItem(int id, int type, String displayName, String material) {
        this.id = id;
        this.type = type;
        this.displayName = displayName;
        this.material = material;
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
