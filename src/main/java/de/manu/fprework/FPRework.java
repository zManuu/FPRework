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

    @Override
    public void onEnable() {
        DatabaseHandler.loadAll();

        try {

            registerCommands(
                    AccountCommand.class
            );

            registerListeners(
                    ConnectionListener.class,
                    ChatListener.class
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SafeVarargs
    private void registerCommands(Class<? extends CommandExecutor>... commandClasses) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Class<? extends CommandExecutor> commandClass : commandClasses) {
            var commandName = commandClass.getSimpleName().replace("Command", "").toLowerCase();
            print("§e[FP] Commands-Load | " + commandName + ": " + commandClass.getSimpleName() + ".java");
            Objects.requireNonNull(getCommand(commandName)).setExecutor((CommandExecutor) commandClass.getConstructors()[0].newInstance());
        }
    }

    @SafeVarargs
    private void registerListeners(Class<? extends Listener>... listeners) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        PluginManager pm = Bukkit.getPluginManager();
        for (Class<? extends Listener> listener : listeners) {
            pm.registerEvents((Listener) listener.getConstructors()[0].newInstance(), this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
