package de.manu.fprework.handler;

import de.manu.fprework.FPRework;
import de.manu.fprework.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InventoryHandler {

    private static final List<CustomInventory> openMenus = new ArrayList<>();

    public static void buildInventory(@NotNull Player player, @NotNull String title, @NotNull InventoryType inventoryType, @Nullable Runnable onClose, CustomInventoryItem... items) {
        var inv = Bukkit.createInventory(null, inventoryType, title);
        for (var item : items) {
            inv.setItem(item.slot, item.itemStack);
        }
        openMenus.add(new CustomInventory(player, inv, onClose, items));

        player.openInventory(inv);
    }

    public static void buildInventory(@NotNull Player player, @NotNull String title, @NotNull InventoryType inventoryType, CustomInventoryItem... items) {
        buildInventory(player, title, inventoryType, null, items);
    }

    public static void onClose(InventoryCloseEvent event) {
        openMenus.stream()
                .filter(e -> e.player.equals(event.getPlayer()))
                .findAny().ifPresent(menu -> {
                    if (menu.onClose != null)
                        menu.onClose.run();
                    openMenus.remove(menu);
                });
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

            if (item.closesInv) {
                openMenus.remove(menu);
                event.getWhoClicked().closeInventory();
            }

            if (item.onClick != null)
                item.onClick.run();
            if (item.cancelClick)
                event.setCancelled(true);
        }
    }

    public static CustomInventoryItem item(int slot, @NotNull ItemStack itemStack, @Nullable Runnable onClick) {
        return new CustomInventoryItem(slot, itemStack, onClick);
    }

    public static CustomInventoryItem item(int slot, @NotNull ItemBuilder itemBuilder, @Nullable Runnable onClick) {
        return new CustomInventoryItem(slot, itemBuilder.build(), onClick);
    }

    public static CustomInventoryItem item(int slot, @NotNull ItemBuilder itemBuilder) {
        return new CustomInventoryItem(slot, itemBuilder.build(), null);
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
        public boolean cancelClick;

        public CustomInventoryItem(int slot, @NotNull ItemStack itemStack, @Nullable Runnable onClick, boolean closesInv, boolean cancelClick) {
            this.slot = slot;
            this.itemStack = itemStack;
            this.onClick = onClick;
            this.closesInv = closesInv;
            this.cancelClick = cancelClick;
        }

        public CustomInventoryItem(int slot, @NotNull ItemStack itemStack, @Nullable Runnable onClick, boolean cancelClick) {
            this.slot = slot;
            this.itemStack = itemStack;
            this.onClick = onClick;
            this.closesInv = false;
            this.cancelClick = cancelClick;
        }

        public CustomInventoryItem(int slot, @NotNull ItemStack itemStack, @Nullable Runnable onClick) {
            this.slot = slot;
            this.itemStack = itemStack;
            this.onClick = onClick;
            this.cancelClick = true;
        }
    }

}
