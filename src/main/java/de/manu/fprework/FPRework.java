package de.manu.fprework;

import de.manu.fprework.handler.DatabaseHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class FPRework extends JavaPlugin {

    @Override
    public void onEnable() {
        DatabaseHandler.loadAll();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
