package de.manu.fprework.models.database;

import de.manu.fprework.utils.javaef.Entity;
import de.manu.fprework.utils.javaef.JavaEFIgnore;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class ServerSkill extends Entity {

    public int id;
    public String name;
    public int requiredLevel;
    public int price;
    public int cooldown;
    public String bindMenuMaterial;
    @JavaEFIgnore
    public Consumer<Player> executor;

    public ServerSkill(int id, String name, int requiredLevel, int price, int cooldown, String bindMenuMaterial) {
        this.id = id;
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.price = price;
        this.cooldown = cooldown;
        this.bindMenuMaterial = bindMenuMaterial;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setExecutor(Consumer<Player> executor) {
        this.executor = executor;
    }
}
