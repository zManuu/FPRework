package de.manu.fprework.listeners;

import de.manu.fprework.utils.Constants;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

/**
 * In this class, all the "default" minecraft events like crafting or eating are canceled.
 */
public class WorldListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(event.getPlayer().getGameMode() != GameMode.CREATIVE);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(event.getPlayer().getGameMode() != GameMode.CREATIVE);
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        event.setCancelled(event.getPlayer().getGameMode() != GameMode.CREATIVE);
    }

    @EventHandler
    public void onTame(EntityTameEvent event) {
        event.setCancelled((event.getOwner() instanceof Player player) && player.getGameMode() != GameMode.CREATIVE);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        event.setCancelled(event.getWhoClicked().getGameMode() != GameMode.CREATIVE);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;

        var action = event.getAction();
        var material = event.getClickedBlock() != null ? event.getClickedBlock().getType() : null;
        var materialName = material != null ? material.name() : null;

        if (action == Action.PHYSICAL && material == Material.FARMLAND) event.setCancelled(true);
        else if (action == Action.RIGHT_CLICK_BLOCK && materialName != null &&
                (Constants.INTERACT_DISALLOWED_BLOCKS.contains(materialName)
                        || materialName.contains("BED")
                        || materialName.contains("TRAPDOOR")
                        || materialName.contains("BUTTON"))) {
            event.setUseInteractedBlock(Event.Result.DENY);
            event.setUseItemInHand(Event.Result.DENY);
            event.setCancelled(true);
        }
        else if (action == Action.LEFT_CLICK_BLOCK && material == Material.ITEM_FRAME) {
            event.setUseInteractedBlock(Event.Result.DENY);
            event.setUseItemInHand(Event.Result.DENY);
            event.setCancelled(true);
        }
    }

    // man kann aus itemframe items nehmen & ihm abbauen
    // man kann aus blumentopf und rüstungsständern nehmen und reinpacken und abbauen

    @EventHandler
    public void onHarvest(PlayerHarvestBlockEvent event) {
        event.setCancelled(event.getPlayer().getGameMode() != GameMode.CREATIVE);
    }

}
