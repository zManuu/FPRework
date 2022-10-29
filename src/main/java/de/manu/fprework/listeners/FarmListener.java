package de.manu.fprework.listeners;

import de.manu.fprework.commands.FarmBlockCommand;
import de.manu.fprework.handler.DatabaseHandler;
import de.manu.fprework.handler.ItemHandler;
import de.manu.fprework.models.database.ServerFarmBlock;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class FarmListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        var player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        var pos = event.getBlock().getLocation();
        var itemStackInHand = player.getInventory().getItemInMainHand();

        var tool = ItemHandler.getItemDataByPDC(itemStackInHand);
        if (tool == null || tool.type != 3) return;

        var farmBlock = DatabaseHandler.ServerFarmBlocks.stream()
                .filter(e -> e.posX == pos.getBlockX() && e.posY == pos.getBlockY() && e.posZ == pos.getBlockZ())
                .findAny()
                .orElse(null);
        if (farmBlock == null) return;

        ItemHandler.addItem(player, farmBlock.itemId, 1);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_BLOCK
                || event.getClickedBlock() == null
                || !FarmBlockCommand.playersInFarmBlockMode.containsKey(player)) return;

        // Farmblock registrieren
        var pos = event.getClickedBlock().getLocation();
        var itemId = FarmBlockCommand.playersInFarmBlockMode.get(player);
        var existing = DatabaseHandler.ServerFarmBlocks.stream()
                .filter(e -> e.posX == pos.getBlockX() && e.posY == pos.getBlockY() && e.posZ == pos.getBlockZ())
                .findAny()
                .orElse(null);

        if (existing != null) return;

        var farmBlock = new ServerFarmBlock(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ(), itemId);
        DatabaseHandler.ServerFarmBlocks.add(farmBlock);
        DatabaseHandler.table(ServerFarmBlock.class).insert(farmBlock);
    }

}
