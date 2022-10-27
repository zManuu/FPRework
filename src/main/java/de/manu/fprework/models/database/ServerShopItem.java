package de.manu.fprework.models.database;

import de.manu.fprework.utils.javaef.Entity;

public class ServerShopItem extends Entity {

    public int id;
    public int shopId;
    public int itemId;
    public int maxItems;
    public int price;
    public int slot;

    public ServerShopItem(int id, int shopId, int itemId, int maxItems, int price, int slot) {
        this.id = id;
        this.shopId = shopId;
        this.itemId = itemId;
        this.maxItems = maxItems;
        this.price = price;
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
