package de.manu.fprework.commands;

import de.manu.fprework.handler.CharacterHandler;
import de.manu.fprework.handler.ItemHandler;
import de.manu.fprework.utils.Constants;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return true;
        if (args.length != 1 && args.length != 2) {
            player.sendMessage(Constants.M_SYNTAX + "/item <itemName> <anzahl?>");
            return true;
        }

        var item = ItemHandler.getItemDataByName(args[0]);
        if (item == null) {
            player.sendMessage(Constants.M_WARNING + "Item wurde nicht gefunden!");
            return true;
        }

        var amount = args.length == 2 && NumberUtils.isNumber(args[1]) ? Integer.parseInt(args[1]) : 1;
        ItemHandler.addItem(player, item.name, amount);
        player.sendMessage(Constants.M_SUCCESS + "Du hast " + amount + "x " + item.name + " erhalten!");

        return false;
    }
}
