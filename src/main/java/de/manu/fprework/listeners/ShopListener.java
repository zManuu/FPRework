package de.manu.fprework.listeners;

import de.manu.fprework.handler.ShopHandler;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ShopListener implements Listener {

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        var entity = event.getRightClicked();
        if (!entity.hasMetadata("NPC")) return;
        var npc = CitizensAPI.getNPCRegistry().getNPC(entity);
        var shop = ShopHandler.shopNpcMap.get(npc.getUniqueId());
        if (shop == null) return;
        ShopHandler.openShop(event.getPlayer(), shop);
    }

}
