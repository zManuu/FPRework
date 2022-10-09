package de.manu.fprework;

import de.manu.fprework.commands.AccountCommand;
import de.manu.fprework.handler.DatabaseHandler;
import de.manu.fprework.listeners.ConnectionListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FPRework extends JavaPlugin {

    @Override
    public void onEnable() {
        DatabaseHandler.loadAll();

        // COMMANDS
        getCommand("account").setExecutor(new AccountCommand());

        // LISTENERS
        registerListeners(
                new ConnectionListener()
        );
    }

    private void registerListeners(Listener... listeners) {
        PluginManager pm = Bukkit.getPluginManager();
        for (Listener listener : listeners) {
            pm.registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
