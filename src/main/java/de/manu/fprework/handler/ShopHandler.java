package de.manu.fprework.handler;

import de.manu.fprework.models.database.ServerItem;
import de.manu.fprework.models.database.ServerShop;
import de.manu.fprework.models.database.ServerShopItem;
import de.manu.fprework.utils.Constants;
import de.manu.fprework.utils.ItemBuilder;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShopHandler {

    public static Map<UUID, ServerShop> shopNpcMap;
    private static Map<Player, Integer> buyAmountMap;

    public static void init() {
        shopNpcMap = new HashMap<>();
        buyAmountMap = new HashMap<>();
        for (var shop : DatabaseHandler.ServerShops) {
            var location = new Location(Constants.WORLD, shop.posX, shop.posY, shop.posZ);
            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, shop.displayName);
            npc.setProtected(true);
            npc.setAlwaysUseNameHologram(true);
            npc.setUseMinecraftAI(false);
            npc.spawn(location);
            shopNpcMap.put(npc.getEntity().getUniqueId(), shop);
        }
    }

    public static void deleteNpcs() {
        for (var npcUUID : shopNpcMap.keySet()) {
            CitizensAPI.getNPCRegistry().getByUniqueId(npcUUID).destroy();
        }
    }

    @NotNull
    public static ServerShopItem[] getShopItems(int shopId) {
        return DatabaseHandler.ServerShopItems.stream()
                .filter(e -> e.shopId == shopId)
                .toArray(ServerShopItem[]::new);
    }

    @Nullable
    public static ServerItem getShopItemServerItem(@NotNull ServerShopItem item) {
        return ItemHandler.getItemDataById(item.itemId);
    }

    public static void openShop(@NotNull Player player, @NotNull ServerShop shop) {
        var shopItems = getShopItems(shop.id);
        var items = new InventoryHandler.CustomInventoryItem[shopItems.length];

        for (var i=0; i<shopItems.length; i++) {
            var shopItem = shopItems[i];
            var itemData = getShopItemServerItem(shopItem);
            var item = ItemHandler.buildItem(itemData, 1);
            items[i] = new InventoryHandler.CustomInventoryItem(shopItem.slot, item,
                    () -> openShopSubMenu(player, shopItem), true, true);
        }

        InventoryHandler.buildInventory(player, shop.displayName, InventoryType.CHEST, items);
    }

    private static void openShopSubMenu(@NotNull Player player, @NotNull ServerShopItem item) {
        var menuItemBuilders = new ItemBuilder[] {
                new ItemBuilder(Material.ARROW, "§c-5"),
                new ItemBuilder(Material.ARROW, "§c-1"),
                new ItemBuilder(Material.ITEM_FRAME, "§aKaufen"),
                new ItemBuilder(Material.ARROW, "§a+1"),
                new ItemBuilder(Material.ARROW, "§a+5")};

        var menuItems = new InventoryHandler.CustomInventoryItem[menuItemBuilders.length];
        buyAmountMap.put(player, 1);

        for (var i=0; i<menuItems.length; i++) {
            var itemBuilder = menuItemBuilders[i];
            var action = switch (i) {
                case 0 -> -1;
                case 1 -> -5;
                case 3 -> 1;
                case 4 -> 5;
                default -> 0;
            };
            menuItems[i] = new InventoryHandler.CustomInventoryItem(i, itemBuilder.build(), () -> {
               if (action == 0) {
                   buyItem(player, item, buyAmountMap.get(player));
                   buyAmountMap.remove(player);
                   return;
               }
               buyAmountMap.put(player, buyAmountMap.get(player) + action);
            }, action == 0, true);
        }

        InventoryHandler.buildInventory(player, "§8§lKauf bestätigen", InventoryType.HOPPER, menuItems);
    }

    private static void buyItem(@NotNull Player player, @NotNull ServerShopItem item, int amount) {
        var price = amount * item.price;
        if (!ItemHandler.doesPlayerHaveItems(player, 3, price)) {
            player.sendMessage(Constants.M_ERROR + "Du kannst dir das nicht leisten!");
            return;
        }
        ItemHandler.removePlayerItems(player, 3, price);
        ItemHandler.addItem(player, item.itemId, amount);
        var itemName = getShopItemServerItem(item).displayName;
        player.sendMessage(Constants.M_SUCCESS + "Du hast " + amount + "x " + itemName + " §agekauft.");
    }

}
