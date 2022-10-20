package de.manu.fprework.handler;

import de.manu.fprework.utils.Constants;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

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

    @EventHandler
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() == EntityType.ARROW) {
            // set bow damage
            if (!event.getDamager().getPersistentDataContainer().has(Constants.KEY_BOW_DAMAGE, PersistentDataType.FLOAT)) return;
            event.setDamage(event.getDamager().getPersistentDataContainer().getOrDefault(Constants.KEY_BOW_DAMAGE, PersistentDataType.FLOAT, 0f));
        }
    }

}
