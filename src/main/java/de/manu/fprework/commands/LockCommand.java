package de.manu.fprework.commands;

import de.manu.fprework.handler.CharacterHandler;
import de.manu.fprework.handler.DatabaseHandler;
import de.manu.fprework.models.CharacterLockedItem;
import de.manu.fprework.utils.Constants;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return true;

        var player = (Player) sender;
        var charId = CharacterHandler.getCharId(player);
        if (charId <= 0) return true;

        var itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand.getData() == null || itemInHand.getItemMeta() == null || itemInHand.getData().getItemType() == Material.AIR) {
            player.sendMessage(Constants.M_WARNING + "Bitte nehme ein Item in die Hand.");
            return true;
        }

        var displayName = itemInHand.getItemMeta().getDisplayName().isEmpty() ? itemInHand.getData().getItemType().name() : itemInHand.getItemMeta().getDisplayName();

        var existingLockedItem = DatabaseHandler.CharacterLockedItems.stream()
                .filter(e -> e.charId == charId && e.itemDisplayName.equals(displayName))
                .findAny()
                .orElse(null);

        if (existingLockedItem != null) {
            DatabaseHandler.CharacterLockedItems.remove(existingLockedItem);
            DatabaseHandler.CharacterLockedItemTable.remove(existingLockedItem);
            player.sendMessage(Constants.M_WARNING + "Das Item ist jetzt nicht mehr gesichert!");
        } else {
            var newLockedItem = new CharacterLockedItem(charId, displayName);
            DatabaseHandler.CharacterLockedItems.add(newLockedItem);
            DatabaseHandler.CharacterLockedItemTable.insert(newLockedItem);
            player.sendMessage(Constants.M_SUCCESS + "Das Item ist jetzt gesichert!");
        }

        return false;
    }
}
