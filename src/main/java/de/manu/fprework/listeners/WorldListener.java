package de.manu.fprework.listeners;

import de.manu.fprework.handler.CharacterHandler;
import de.manu.fprework.handler.DatabaseHandler;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class WorldListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        var player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        var player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        var player = event.getPlayer();
        var charId = CharacterHandler.getCharId(player);
        var itemStack = event.getItemDrop().getItemStack();

        if (itemStack.getData() == null || itemStack.getData() == null) return;

        var itemName = itemStack.getItemMeta().getDisplayName().isEmpty() ? itemStack.getData().getItemType().name() : itemStack.getItemMeta().getDisplayName();

        DatabaseHandler.CharacterLockedItems.stream()
                .filter(e -> e.charId == charId && e.itemDisplayName.equals(itemName))
                .findAny()
                .ifPresent(e -> event.setCancelled(true));
    }

}
