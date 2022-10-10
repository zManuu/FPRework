package de.manu.fprework.listeners;

import de.manu.fprework.handler.InventoryHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        InventoryHandler.onClick(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryHandler.onClose(event);
    }

}
