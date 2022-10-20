package de.manu.fprework.listeners;

import de.manu.fprework.utils.Constants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.persistence.PersistentDataType;

public class WeaponListener implements Listener {

    @EventHandler
    public void onArrowShot(EntityShootBowEvent event) {
        if (event.getBow() != null) {
            // transfer pdc damage value
            var itemMeta = event.getBow().getItemMeta();
            if (itemMeta == null || !itemMeta.getPersistentDataContainer().has(Constants.KEY_BOW_DAMAGE, PersistentDataType.FLOAT)) return;
            var pdcValue = itemMeta.getPersistentDataContainer().getOrDefault(Constants.KEY_BOW_DAMAGE, PersistentDataType.FLOAT, 0f);
            event.getProjectile().getPersistentDataContainer().set(Constants.KEY_BOW_DAMAGE, PersistentDataType.FLOAT, pdcValue);
        }
    }

}
