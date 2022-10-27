package de.manu.fprework.commands;

import de.manu.fprework.handler.DatabaseHandler;
import de.manu.fprework.handler.ShopHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadDBCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) return true;

        DatabaseHandler.loadAll();
        ShopHandler.deleteNpcs();
        ShopHandler.init();

        return false;
    }
}
