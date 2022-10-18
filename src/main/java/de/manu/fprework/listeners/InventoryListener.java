package de.manu.fprework.listeners;

import de.manu.fprework.handler.CharacterHandler;
import de.manu.fprework.handler.DatabaseHandler;
import de.manu.fprework.handler.InventoryHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

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

}
