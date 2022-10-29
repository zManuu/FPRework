package de.manu.fprework.commands;

import de.manu.fprework.handler.ItemHandler;
import de.manu.fprework.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FarmBlockCommand implements CommandExecutor {

    public static Map<Player, Integer> playersInFarmBlockMode = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return true;

        if (playersInFarmBlockMode.containsKey(player)) {
            playersInFarmBlockMode.remove(player);
            player.sendMessage(Constants.M_SUCCESS + "Du bist nicht mehr im FarmBlock-Modus.");
        } else {
            if (args.length != 1) {
                player.sendMessage(Constants.M_ERROR + "Syntax: /farmblock <itemName>");
                return true;
            }
            var itemData = ItemHandler.getItemDataByName(args[0]);
            if (itemData == null) {
                player.sendMessage(Constants.M_ERROR + "Item existiert nicht!");
                return true;
            }
            playersInFarmBlockMode.put(player, itemData.id);
            player.sendMessage(Constants.M_WARNING + "Du bist im FarmBlock-Modus.");
        }

        return false;
    }
}
