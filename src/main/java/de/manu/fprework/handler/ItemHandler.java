package de.manu.fprework.handler;

import de.manu.fprework.models.CharacterItem;
import de.manu.fprework.models.ServerItem;
import de.manu.fprework.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemHandler {

    public static List<CharacterItem> getInventoryItems(int charId) {
        return DatabaseHandler.CharacterItems.stream()
                .filter(e -> e.charId == charId)
                .toList();
    }

    @Nullable
    public static ServerItem getItemData(int itemId) {
        return DatabaseHandler.ServerItems.stream()
                .filter(e -> e.id == itemId)
                .findAny()
                .orElse(null);
    }

    private static ItemStack buildItem(CharacterItem item) {
        var itemType = getItemData(item.itemId);
        return new ItemBuilder(Material.valueOf(itemType.material), itemType.displayName).amount(item.amount).build();
    }

    public static void reloadInventory(Player player) {
        player.getInventory().clear();
        var charId = CharacterHandler.getCharId(player);
        for (var item : getInventoryItems(charId)) {
            player.getInventory().setItem(item.slot, buildItem(item));
        }
    }

    public static void addItem(Player player, int itemId, int amount, int slot) {
        var item = new CharacterItem(CharacterHandler.getCharId(player), itemId, amount, slot);
        DatabaseHandler.CharacterItems.add(item);
        DatabaseHandler.CharacterItemTable.insert(item);

        var itemStack = buildItem(item);
        player.getInventory().setItem(slot, itemStack);
    }

}
