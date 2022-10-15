package de.manu.fprework.handler;

import de.manu.fprework.FPRework;
import de.manu.fprework.models.Character;
import de.manu.fprework.models.CharacterClass;
import de.manu.fprework.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

public class CharacterHandler {

    public static Character getSelectedCharacter(int accountId) {
        var selectedCharacterId = AccountHandler.getAccount(accountId).selectedChar;
        return DatabaseHandler.Characters.stream()
                .filter(e -> e.id == selectedCharacterId)
                .findAny()
                .orElse(null);
    }

    public static int getCharId(Player player) {
        return AccountHandler.getAccount(player).selectedChar;
    }

    public static void openSelectCharTypeMenu(Player player) {
        InventoryHandler.buildInventory(player, "§lWähle einen Charakter-Typen", InventoryType.HOPPER, () -> {
                    openSelectCharTypeMenu(player);
                },
                new InventoryHandler.CustomInventoryItem(1, new ItemStack(Material.STICK), () -> {
                    createChar(player, 1);
                }),
                new InventoryHandler.CustomInventoryItem(2, new ItemStack(Material.BOW), () -> {
                    createChar(player, 2);
                }),
                new InventoryHandler.CustomInventoryItem(3, new ItemStack(Material.STONE_SWORD), () -> {
                    createChar(player, 3);
                }));
    }

    private static void createChar(Player player, int characterClass) {
        var account = AccountHandler.getAccount(player);
        var character = new Character(account.id, characterClass, 0, 0);
        DatabaseHandler.Characters.add(character);
        DatabaseHandler.CharacterTable.insert(character);
        account.setSelectedChar(character.id);
        DatabaseHandler.AccountTable.save(account);
        player.sendMessage(Constants.M_SUCCESS + "Dein Charakter ist erstellt worden!");
        player.setInvisible(false);
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1, 1);
    }

}
