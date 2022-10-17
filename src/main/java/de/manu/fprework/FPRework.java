package de.manu.fprework;

import de.manu.fprework.commands.*;
import de.manu.fprework.handler.*;
import de.manu.fprework.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class FPRework extends JavaPlugin {

    public static void print(String msg) {
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    public static FPRework INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        DatabaseHandler.loadAll();
        PlaytimeHandler.init();
        WorldHandler.init();

        registerCommands(
                AccountCommand.class,
                LockCommand.class
        );

        registerListeners(
                ConnectionListener.class,
                ChatListener.class,
                InventoryListener.class,
                WorldListener.class,
                DamageListener.class
        );
    }

    @SafeVarargs
    private void registerCommands(Class<? extends CommandExecutor>... commandClasses) {
        for (Class<? extends CommandExecutor> commandClass : commandClasses) {
            var commandName = commandClass.getSimpleName().replace("Command", "").toLowerCase();
            print("§e[FP] Command-Load | " + commandName + ": " + commandClass.getSimpleName() + ".java");
            try {
                Objects.requireNonNull(getCommand(commandName)).setExecutor((CommandExecutor) commandClass.getConstructors()[0].newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SafeVarargs
    private void registerListeners(Class<? extends Listener>... listeners) {
        PluginManager pm = Bukkit.getPluginManager();
        for (Class<? extends Listener> listener : listeners) {
            print("§b[FP] Listener-Load | " + listener.getSimpleName());
            try {
                pm.registerEvents((Listener) listener.getConstructors()[0].newInstance(), this);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
