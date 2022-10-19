package de.manu.fprework.models;

import de.manu.fprework.utils.javaef.Entity;

public class ServerItem extends Entity {

    public int id;
    public int type; // 1: Consumable, 2: Weapon, 3: Tool, 4: Util, 5: Static
    public String name;
    public String displayName;
    public String material;
    public int tier; // 0: Keine, 1: Gewöhnlich, 2: Selten, 3: Episch, 4: Legendär

    public ServerItem(int type, String name, String displayName, String material, int tier) {
        this.type = type;
        this.name = name;
        this.displayName = displayName;
        this.material = material;
        this.tier = tier;
    }

    public ServerItem(int id, int type, String name, String displayName, String material, int tier) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.displayName = displayName;
        this.material = material;
        this.tier = tier;
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
