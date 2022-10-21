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
    @JavaEFIgnore
    public Consumer<Player> executor;

    public ServerSkill(int id, String name, int requiredLevel, int price) {
        this.id = id;
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.price = price;
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
