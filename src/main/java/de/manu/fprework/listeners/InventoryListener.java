package de.manu.fprework.listeners;

import de.manu.fprework.handler.*;
import de.manu.fprework.utils.Constants;
import de.manu.fprework.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InventoryListener implements Listener {

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        // Item is locked through server? (HELPBOOK/CHAR-MANAGEMENT)
        var itemStack = event.getCurrentItem();
        if (itemStack == null || itemStack.getItemMeta() == null || itemStack.getItemMeta().getDisplayName().isEmpty()) return;
        var itemName = itemStack.getItemMeta().getDisplayName();
        DatabaseHandler.ServerItems.stream()
                .filter(e -> e.displayName.equals(itemName))
                .findAny()
                .ifPresent(e -> event.setCancelled(e.type == 5));

        InventoryHandler.onClick(event); // Custom Menus
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryHandler.onClose(event); // Custom Menus
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        var player = event.getPlayer();
        var charId = CharacterHandler.getCharId(player);
        var itemStack = event.getItemDrop().getItemStack();

        if (itemStack.getItemMeta() == null || itemStack.getItemMeta().getDisplayName().isEmpty()) return;

        var itemName = itemStack.getItemMeta().getDisplayName();

        // Item is locked manually?
        DatabaseHandler.CharacterLockedItems.stream()
                .filter(e -> e.charId == charId && e.itemDisplayName.equals(itemName))
                .findAny()
                .ifPresent(e -> event.setCancelled(true));

        // Item is locked through server? (HELPBOOK/CHAR-MANAGEMENT)
        DatabaseHandler.ServerItems.stream()
                .filter(e -> e.displayName.equals(itemName))
                .findAny()
                .ifPresent(e -> event.setCancelled(e.type == 5));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Right-click
            var item = player.getInventory().getItemInMainHand();
            var itemType = ItemHandler.getItemDataByPDC(item);
            if (itemType == null) return;
            if (itemType.type == 1) {
                // Consumable
                var cStats = ItemHandler.getItemStatsConsumable(itemType);
                if (cStats == null) return;
                player.setFoodLevel(player.getFoodLevel() + cStats.hunger);
                player.setHealth(Math.min(player.getHealth() + cStats.hearts, player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
                if (!cStats.effectName.isEmpty()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(cStats.effectName), cStats.effectDuration, cStats.effectStrength));
                }
                item.setAmount(item.getAmount() - 1);
            } else if (itemType.type == 5) {
                // Static
                if (itemType.name.equals("static_Questbook")) {
                    InventoryHandler.buildInventory(player, "§9§lQuests", InventoryType.CHEST);
                } else if (itemType.name.equals("static_Charactermanagement")) {
                    InventoryHandler.buildInventory(player, "§9§lCharakter-Verwaltung", InventoryType.CHEST,
                            InventoryHandler.item(11, new ItemBuilder(Material.BLAZE_ROD, "§5§lSkills"), () -> SkillsHandler.openSkillBindMenu(player)),
                            InventoryHandler.item(12, new ItemBuilder(Material.GHAST_TEAR, "§b§lSoulpoints: 3")),
                            InventoryHandler.item(13, new ItemBuilder(Material.FILLED_MAP, "§7§lStats")),
                            InventoryHandler.item(14, new ItemBuilder(Material.COMPASS, "§e§lSpielzeit")),
                            InventoryHandler.item(15, new ItemBuilder(Material.SHIELD, "§c§lClan")));
                }
            }
        }
    }

}
