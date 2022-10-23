package de.manu.fprework.listeners;

import de.manu.fprework.utils.Constants;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;

/**
 * In this class, all the "default" minecraft events like crafting or eating are canceled.
 * Todo: You can still eat cake
 * Todo: Breaking a block resets the texture (owner) (?? Maybe not possible ??)
 * Todo: Composer
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
                        || materialName.contains("GATE")
                        || materialName.contains("POTTED")
                        || materialName.contains("BUTTON"))) {
            event.setUseInteractedBlock(Event.Result.DENY);
            event.setUseItemInHand(Event.Result.DENY);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(event.getRightClicked().getType() == EntityType.ITEM_FRAME);
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(event.getRightClicked().getType() == EntityType.ITEM_FRAME);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;

        if (event.getEntityType() == EntityType.ARMOR_STAND || event.getEntityType() == EntityType.ITEM_FRAME) {
            event.setDamage(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
        if (!(event.getRemover() instanceof Player player)) return;
        event.setCancelled(player.getGameMode() != GameMode.CREATIVE);
    }

    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(event.getPlayer().getGameMode() != GameMode.CREATIVE);
    }

    @EventHandler
    public void onHarvest(PlayerHarvestBlockEvent event) {
        event.setCancelled(event.getPlayer().getGameMode() != GameMode.CREATIVE);
    }

}
