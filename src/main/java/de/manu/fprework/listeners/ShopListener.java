package de.manu.fprework.listeners;

import de.manu.fprework.handler.ShopHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        var entity = event.getRightClicked();
        if (!entity.hasMetadata("NPC")) return;
        var shop = ShopHandler.shopNpcMap.get(entity.getUniqueId());
        if (shop == null) return;
        ShopHandler.openShop(event.getPlayer(), shop);
    }

}
