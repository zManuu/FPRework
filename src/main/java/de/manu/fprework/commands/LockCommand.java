package de.manu.fprework.commands;

import de.manu.fprework.handler.CharacterHandler;
import de.manu.fprework.handler.DatabaseHandler;
import de.manu.fprework.models.CharacterLockedItem;
import de.manu.fprework.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return true;

        var item = player.getInventory().getItemInMainHand();

        if (item.getItemMeta() == null || item.getItemMeta().getDisplayName().isEmpty()) {
            player.sendMessage(Constants.M_WARNING + "Dieses Item kannst du nicht locken.");
            return true;
        }

        var displayName = item.getItemMeta().getDisplayName();
        var charId = CharacterHandler.getCharId(player);

        // Item is locked through server? (HELPBOOK/CHAR-MANAGEMENT)
        var staticItem = DatabaseHandler.ServerItems.stream()
                .filter(e -> e.displayName.equals(displayName))
                .findAny()
                .orElse(null);
        if (staticItem != null && staticItem.type == 5) {
            player.sendMessage(Constants.M_WARNING + "Dieses Item kannst du nicht locken.");
            return true;
        }

        var existingLockedItem = DatabaseHandler.CharacterLockedItems.stream()
                .filter(e -> e.charId == charId && e.itemDisplayName.equals(displayName))
                .findAny()
                .orElse(null);

        if (existingLockedItem != null) {
            DatabaseHandler.CharacterLockedItems.remove(existingLockedItem);
            DatabaseHandler.table(CharacterLockedItem.class).remove(existingLockedItem);
            player.sendMessage(Constants.M_WARNING + "Das Item ist jetzt nicht mehr gesichert!");
        } else {
            var newLockedItem = new CharacterLockedItem(charId, displayName);
            DatabaseHandler.CharacterLockedItems.add(newLockedItem);
            DatabaseHandler.table(CharacterLockedItem.class).insert(newLockedItem);
            player.sendMessage(Constants.M_SUCCESS + "Das Item ist jetzt gesichert!");
        }

        return false;
    }
}
