package de.manu.fprework.handler;

import de.manu.fprework.FPRework;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryHandler {

    private static final List<CustomInventory> openMenus = new ArrayList<>();

    public static void buildInventory(Player player, String title, InventoryType inventoryType, Runnable onClose, CustomInventoryItem... items) {
        var inv = Bukkit.createInventory(null, inventoryType, title);
        for (var item : items) {
            inv.setItem(item.slot, item.itemStack);
        }
        openMenus.add(new CustomInventory(player, inv, onClose, items));

        player.openInventory(inv);
    }

    public static void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Bukkit.getScheduler().runTaskLater(FPRework.INSTANCE, () -> {
            openMenus.stream()
                    .filter(e -> e.player.equals(event.getPlayer()))
                    .findAny().ifPresent(menu -> {
                        menu.onClose.run();
                        openMenus.remove(menu);
                    });
        }, 10);
    }

    public static void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        var menu = openMenus.stream()
                .filter(e -> e.player.equals(event.getWhoClicked()))
                .findAny()
                .orElse(null);

        if (menu == null) return;

        for (var item : menu.items) {
            var slot = item.slot;
            if (event.getSlot() != slot) continue;

            item.onClick.run();
            if (item.closesInv) {
                event.getWhoClicked().closeInventory();
                openMenus.remove(menu);
            }
        }
    }

    public static class CustomInventory {
        public Player player;
        public Inventory inventory;
        public Runnable onClose;
        public CustomInventoryItem[] items;

        public CustomInventory(Player player, Inventory inventory, Runnable onClose, CustomInventoryItem[] items) {
            this.player = player;
            this.inventory = inventory;
            this.onClose = onClose;
            this.items = items;
        }
    }

    public static class CustomInventoryItem {
        public int slot;
        public ItemStack itemStack;
        public Runnable onClick;
        public boolean closesInv;

        public CustomInventoryItem(int slot, ItemStack itemStack, Runnable onClick, boolean closesInv) {
            this.slot = slot;
            this.itemStack = itemStack;
            this.onClick = onClick;
            this.closesInv = closesInv;
        }

        public CustomInventoryItem(int slot, ItemStack itemStack, Runnable onClick) {
            this.slot = slot;
            this.itemStack = itemStack;
            this.onClick = onClick;
            this.closesInv = true;
        }
    }

}
