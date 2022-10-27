package de.manu.fprework;

import de.manu.fprework.commands.*;
import de.manu.fprework.handler.*;
import de.manu.fprework.listeners.*;
import de.manu.fprework.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class FPRework extends JavaPlugin implements Listener {

    public static void print(@NotNull String msg) {
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    public static void setTimeout(@NotNull Runnable runnable, int ticks) {
        Bukkit.getScheduler().runTaskLater(INSTANCE, runnable, ticks);
    }

    public static void setInterval(@NotNull Runnable runnable, int ticks) {
        Bukkit.getScheduler().runTaskTimer(INSTANCE, runnable, 0, ticks);
    }

    public static FPRework INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Constants.KEY_ITEM_ID = new NamespacedKey(this, "ITEM_ID");
        Constants.KEY_BOW_DAMAGE = new NamespacedKey(this, "BOW_DAMAGE");

        DatabaseHandler.loadAll();
        WorldHandler.init();
        SkillsHandler.init();
        ActionbarHandler.init();
        ShopHandler.init();

        registerCommands(
                AccountCommand.class,
                LockCommand.class,
                ItemCommand.class,
                ReloadDBCommand.class
        );

        registerListeners(
                ConnectionListener.class,
                ChatListener.class,
                InventoryListener.class,
                WorldListener.class,
                DamageListener.class,
                WeaponListener.class,
                SkillListener.class,
                ShopListener.class
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
        ShopHandler.deleteNpcs();
    }

}
