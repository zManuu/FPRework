package de.manu.fprework.handler;

import de.manu.fprework.utils.Constants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDroppedExp(0);
        event.setKeepInventory(true);
        event.setNewExp(0);
        event.setNewLevel(0);
        event.setDeathMessage("§8[§c†§8] §c" + event.getEntity().getName());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(Constants.SPAWN);
    }

}
