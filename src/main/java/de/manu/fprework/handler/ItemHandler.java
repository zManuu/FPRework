package de.manu.fprework.handler;

import de.manu.fprework.models.ServerItem;
import de.manu.fprework.models.ServerItemStatsConsumable;
import de.manu.fprework.utils.Constants;
import de.manu.fprework.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

public class ItemHandler {

    @Nullable
    public static ServerItem getItemDataById(int itemId) {
        return DatabaseHandler.ServerItems.stream()
                .filter(e -> e.id == itemId)
                .findAny()
                .orElse(null);
    }

    @Nullable
    public static ServerItem getItemDataByName(String itemName) {
        return DatabaseHandler.ServerItems.stream()
                .filter(e -> e.name.equalsIgnoreCase(itemName))
                .findAny()
                .orElse(null);
    }

    /**
     * NOT RECOMMENDED!
     * There is many Items that share a displayName.
     * Only use if 100% sure that there is only the one.
     */
    @Nullable
    public static ServerItem getItemDataByDisplayName(String displayName) {
        return DatabaseHandler.ServerItems.stream()
                .filter(e -> e.displayName.equalsIgnoreCase(displayName))
                .findAny()
                .orElse(null);
    }

    /**
     * Gets the Item-ID through the PersistentDataContainer-API.
     * The Item-ID has to be stored in the PersistentDataContainer-API before.
     */
    @Nullable
    public static ServerItem getItemDataByPDC(ItemStack itemStack) {
        if (itemStack == null || itemStack.getItemMeta() == null) return null;
        var container = itemStack.getItemMeta().getPersistentDataContainer();
        if (!container.has(Constants.KEY_ITEM_ID, PersistentDataType.INTEGER)) return null;
        var itemId = container.getOrDefault(Constants.KEY_ITEM_ID, PersistentDataType.INTEGER, -1);
        return getItemDataById(itemId);
    }

    public static String getItemTypeName(int itemType) {
        return switch (itemType) {
            case 1 -> "§eKonsumierbares";
            case 2 -> "§cWaffen";
            case 3 -> "§7Werkzeug";
            case 4 -> "§bSonstiges";
            case 5 -> "§3§lServer Hilfe";
            default -> "§fN/A";
        };
    }

    public static String getItemTierName(int tier) {
        return switch(tier) {
          case 1 -> "§7§lGewöhnlich";
          case 2 -> "§a§lSelten";
          case 3 -> "§5§lEpisch";
          case 4 -> "§1§lLegendär";
          default -> "§fN/A";
        };
    }

    public static ItemStack buildItem(ServerItem item, int amount) {
        var builder = new ItemBuilder(Material.valueOf(item.material));
        builder.databaseId(item.id);
        builder.amount(amount);
        builder.displayname(item.displayName);
        builder.lore(" ");
        if (item.tier > 0) {
            builder.lore(" §8§l["+ getItemTierName(item.tier) + "§8§l]");
            builder.lore(" ");
        }
        builder.lore(" §8➥ §7Typ: " + getItemTypeName(item.type));
        switch (item.type) {
            case 1:
                var cStats = getItemStatsConsumable(item);
                if (cStats != null) {
                    if (cStats.hunger > 0) builder.lore(" §8➥ §7Essen: §e" + cStats.hunger);
                    if (cStats.hearts > 0) builder.lore(" §8➥ §7Heilung: §e" + cStats.hearts);
                    if (!cStats.effectName.isEmpty()) {
                        builder.lore(" §8➥ §7Effekt:");
                        builder.lore("   §8➥ §7Name: §e" + cStats.effectName);
                        builder.lore("   §8➥ §7Dauer: §e" + cStats.effectDuration);
                        builder.lore("   §8➥ §7Stärke: §e" + cStats.effectStrength);
                    }
                }
                break;
        }
        builder.lore(" ");
        return builder.build();
    }

    public static void setItem(Player player, String itemName, int amount, int slot) {
        var itemStack = buildItem(getItemDataByName(itemName), amount);
        player.getInventory().setItem(slot, itemStack);
    }

    public static void addItem(Player player, String itemName, int amount) {
        var itemStack = buildItem(getItemDataByName(itemName), amount);
        player.getInventory().addItem(itemStack);
    }

    @Nullable
    public static ServerItemStatsConsumable getItemStatsConsumable(ServerItem item) {
        return DatabaseHandler.ServerItemStatsConsumable.stream()
                .filter(e -> e.itemId == item.id)
                .findAny()
                .orElse(null);
    }

}
